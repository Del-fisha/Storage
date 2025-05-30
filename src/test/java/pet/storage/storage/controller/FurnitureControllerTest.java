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
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;
import pet.storage.storage.service.FurnitureCrudService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FurnitureController.class)
@ContextConfiguration(classes = {FurnitureController.class, GlobalExceptionHandler.class})
class FurnitureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FurnitureCrudService furnitureCrudService;

    @Autowired
    private ObjectMapper objectMapper;

    FurnitureDTO mockFurniture;

    @BeforeEach
    void setUp() {
        mockFurniture = new FurnitureDTO(
                "Диван",
                "IKEA",
                Category.Furniture,
                Metric.Piece,
                1.0,
                25000.0,
                LocalDate.of(2024, 11, 10),
                "Удобный трехместный диван с чехлом из хлопка"
        );
    }

    @AfterEach
    void tearDown() {
        mockFurniture = null;
    }

    @Test
    @DisplayName("Получение мебели по ID возвращает FurnitureDTO")
    void shouldReturnFurnitureById() throws Exception {
        int idToFind = 1;
        String furnitureName = "Диван";
        when(furnitureCrudService.findById(anyInt())).thenReturn(mockFurniture);

        mockMvc.perform(get("/storage_api/furniture/id/{id}", idToFind))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(furnitureName));

        verify(furnitureCrudService, times(1)).findById(idToFind);
    }

    @Test
    @DisplayName("Получение мебели по несуществующему ID выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenFurnitureNotFoundById() throws Exception {
        when(furnitureCrudService.findById(anyInt())).thenThrow(ItemNotFoundException.class);

        int idToFind = 999;
        mockMvc.perform(get("/storage_api/furniture/id/{id}", idToFind))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class,
                        result.getResolvedException()))
                .andExpect(jsonPath("$.message").value("Такой товар не найден"))
                .andExpect(jsonPath("$.code").value("ItemNotFoundException"));

        verify(furnitureCrudService, times(1)).findById(idToFind);
    }

    @Test
    @DisplayName("Получение мебели по названию возвращает FurnitureDTO")
    void shouldReturnFurnitureByName() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Получение мебели по несуществующему названию выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenFurnitureNotFoundByName() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Получение списка всей мебели возвращает список FurnitureDTO")
    void shouldReturnAllFurniture() throws Exception {

        FurnitureDTO mockFurniture1 = new FurnitureDTO(
                "Стол",
                "LoftDesign",
                Category.Furniture,
                Metric.Piece,
                1.0,
                15000.0,
                LocalDate.of(2023, 5, 5),
                "Обеденный стол из массива дерева с металлическими ножками"
        );

        FurnitureDTO mockFurniture2 = new FurnitureDTO(
                "Кресло",
                "ComfortPlus",
                Category.Furniture,
                Metric.Piece,
                1.0,
                12000.0,
                LocalDate.of(2025, 1, 20),
                "Эргономичное кресло с поддержкой поясницы"
        );

        List<FurnitureDTO> furnitureDTOList = List.of(mockFurniture, mockFurniture1, mockFurniture2);
    }

    @Test
    @DisplayName("Создание новой мебели возвращает FurnitureDTO")
    void shouldCreateFurniture() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Создание уже существующей мебели выбрасывает ItemAlreadyExistsException")
    void shouldThrowExceptionWhenCreatingDuplicateFurniture() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Обновление существующей мебели возвращает FurnitureDTO")
    void shouldUpdateFurniture() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Обновление несуществующей мебели выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenUpdatingNonExistentFurniture() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Удаление несуществующей мебели выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenDeletingNonExistentFurniture() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Удаление мебели по валидному ID возвращает NoContent")
    void shouldDeleteFurnitureById() throws Exception {
        // ...
    }
}
