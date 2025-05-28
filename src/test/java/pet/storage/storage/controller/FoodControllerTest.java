package pet.storage.storage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pet.storage.storage.aop.GlobalExceptionHandler;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;
import pet.storage.storage.service.FoodCrudService;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(FoodController.class)
@ContextConfiguration(classes = {FoodController.class, GlobalExceptionHandler.class})
class FoodControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FoodCrudService foodCrudService;

    @Autowired
    private ObjectMapper objectMapper;

    FoodDTO mockFood;

    @BeforeEach
    void setUp() {
        mockFood = new FoodDTO("Молоко", "Ферма 'Ромашка'", Category.Food, Metric.L, 1.0,
                80.0, LocalDate.of(2025, 5, 20),
                "Консервированная фасоль",
                LocalDate.of(2025, 2, 15),
                LocalDate.of(2026, 2, 15));
    }

    @AfterEach
    void tearDown() {
        mockFood = null;
    }

    @Test
    @DisplayName("Получение продукта по ID возвращает FoodDTO")
    void shouldReturnFoodById() throws Exception {
        when(foodCrudService.findById(any(Integer.class))).thenReturn(mockFood);
        int idToFind = 1;

        mockMvc.perform(get("/storage_api/food/id/{id}", idToFind))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(mockFood.getName()));

        verify(foodCrudService, times(1)).findById(idToFind);
    }

    @Test
    @DisplayName("Получение продукта по несуществующему ID выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenFoodNotFoundById() throws Exception {
        when(foodCrudService.findById(any(Integer.class))).thenThrow(new ItemNotFoundException());
        int idToFind = 4;
        mockMvc.perform(get("/storage_api/food/id/{id}", idToFind))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class,
                        result.getResolvedException()))
                .andExpect(result -> assertEquals("Такой товар не найден",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(foodCrudService, times(1)).findById(idToFind);
    }

    @Test
    @DisplayName("Получение продукта по названию возвращает FoodDTO")
    void shouldReturnFoodByName() throws Exception {
        when(foodCrudService.findByName(any(String.class))).thenReturn(mockFood);

        String nameToFind = "Молоко";

        mockMvc.perform(get("/storage_api/food/name/{name}", nameToFind))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(nameToFind));

        verify(foodCrudService, times(1)).findByName(nameToFind);
    }

    @Test
    @DisplayName("Получение продукта по несуществующему названию выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenFoodNotFoundByName() throws Exception {
        when(foodCrudService.findByName(any(String.class))).thenThrow(new ItemNotFoundException());

        String nameToFind = "Котлеты";

        mockMvc.perform(get("/storage_api/food/name/{name}", nameToFind))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class,
                        result.getResolvedException()))
                .andExpect(result -> assertEquals("Такой товар не найден",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(foodCrudService, times(1)).findByName(nameToFind);
    }

    @Test
    @DisplayName("Получение списка всех продуктов возвращает список FoodDTO")
    void shouldReturnAllFood() throws Exception {
        FoodDTO foodDTO1 = new FoodDTO(
                "Бородинский хлеб",
                "Пекарня №5",
                Category.Food,
                Metric.Piece,
                1.0,
                45.0,
                LocalDate.of(2025, 5, 28),
                "Ржаной хлеб",
                LocalDate.of(2025, 5, 27),
                LocalDate.of(2025, 6, 2)
        );

        FoodDTO foodDTO2 = new FoodDTO(
                "Фасоль в томате",
                "ГлавКонсерв",
                Category.Food,
                Metric.Kg,
                0.5,
                120.0,
                LocalDate.of(2025, 5, 20),
                 "Консервированная фасоль",
                LocalDate.of(2025, 2, 15),
                LocalDate.of(2026, 2, 15)
        );

        List<FoodDTO> foodDTOList = List.of(foodDTO1, foodDTO2, mockFood);

        when(foodCrudService.findAll()).thenReturn(foodDTOList);

        mockMvc.perform(get("/storage_api/food/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));

        verify(foodCrudService, times(1)).findAll();
    }

    @Test
    @DisplayName("Создание нового продукта возвращает FoodDTO")
    void shouldCreateFood() throws Exception {
        FoodDTO dtoToTest = new FoodDTO("Молоко", "Ферма 'Ромашка'", Category.Food, Metric.L, 1.0,
                80.0, LocalDate.of(2025, 5, 20),
                "Консервированная фасоль",
                LocalDate.of(2025, 2, 15),
                LocalDate.of(2026, 2, 15));

        when(foodCrudService.save(any(FoodDTO.class))).thenReturn(mockFood);

        mockMvc.perform(post("/storage_api/food/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoToTest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(mockFood.getName()))
                .andExpect(jsonPath("$.amount").value(mockFood.getAmount()));

        verify(foodCrudService, times(1)).save(any(FoodDTO.class));
    }

    @Test
    @DisplayName("Создание уже существующего продукта выбрасывает ItemAlreadyExistsException")
    void shouldThrowExceptionWhenCreatingDuplicateFood() throws Exception {
        FoodDTO dtoToTest = new FoodDTO("Молоко", "Ферма 'Ромашка'", Category.Food, Metric.L, 1.0,
                80.0, LocalDate.of(2025, 5, 20),
                "Консервированная фасоль",
                LocalDate.of(2025, 2, 15),
                LocalDate.of(2026, 2, 15));

        when(foodCrudService.save(any(FoodDTO.class))).thenThrow(new ItemAlreadyExistsException());

        mockMvc.perform(post("/storage_api/food/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoToTest)))
                .andExpect(status().isConflict())
                .andExpect(result -> assertInstanceOf(ItemAlreadyExistsException.class,
                        result.getResolvedException()))
                .andExpect(result -> assertEquals("Такая позиция уже есть на складе",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("Обновление существующего продукта возвращает FoodDTO")
    void shouldUpdateFood() throws Exception {
        FoodDTO dtoToTest = new FoodDTO("Молоко", "Ферма 'Ромашка'", Category.Food, Metric.L, 3.0,
                80.0, LocalDate.of(2025, 5, 20),
                "Консервированная фасоль",
                LocalDate.of(2025, 2, 15),
                LocalDate.of(2026, 2, 15));

        when(foodCrudService.update(any(FoodDTO.class))).thenReturn(mockFood);

        mockFood.setAmount(mockFood.getAmount() + 2);

        mockMvc.perform(put("/storage_api/food/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoToTest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(mockFood.getName()))
                .andExpect(jsonPath("$.amount").value(mockFood.getAmount()));

        verify(foodCrudService, times(1)).update(any(FoodDTO.class));
    }

    @Test
    @DisplayName("Обновление несуществующего продукта выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenUpdatingNonExistentFood() throws Exception {
        FoodDTO dtoToTest = new FoodDTO("Молоко", "Ферма 'Ромашка'", Category.Food, Metric.L, 1.0,
                80.0, LocalDate.of(2025, 5, 20),
                "Консервированная фасоль",
                LocalDate.of(2025, 2, 15),
                LocalDate.of(2026, 2, 15));

        when(foodCrudService.update(any(FoodDTO.class))).thenThrow(new ItemNotFoundException());

        mockMvc.perform(put("/storage_api/food/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoToTest)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class,
                        result.getResolvedException()))
                .andExpect(result -> assertEquals("Такой товар не найден",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("Удаление несуществующего продукта выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenDeletingNonExistentFood() throws Exception {
        doThrow(new ItemNotFoundException()).when(foodCrudService).delete(anyInt());

        int idToDelete = 99;

        mockMvc.perform(delete("/storage_api/food/{id}",idToDelete))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class,
                        result.getResolvedException()));

        verify(foodCrudService, times(1)).delete(idToDelete);
    }

    @Test
    @DisplayName("Удаление продукта по валидному ID возвращает NoContent")
    void shouldDeleteFoodById() throws Exception {
        doNothing().when(foodCrudService).delete(anyInt());
        int idToDelete = 1;
        mockMvc.perform(delete("/storage_api/food/{id}",idToDelete))
                .andExpect(status().isNoContent());
        verify(foodCrudService, times(1)).delete(idToDelete);
    }
}