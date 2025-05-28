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
                80.0, LocalDate.now(), "Свежее коровье молоко",
                LocalDate.now().minusDays(2),  // Дата производства
                LocalDate.now().plusDays(5));   // Срок годности);
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
    }

    @Test
    @DisplayName("Получение продукта по несуществующему ID выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenFoodNotFoundById() throws Exception {
        // TODO
    }

    @Test
    @DisplayName("Получение продукта по названию возвращает FoodDTO")
    void shouldReturnFoodByName() throws Exception {
        // TODO
    }

    @Test
    @DisplayName("Получение продукта по несуществующему названию выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenFoodNotFoundByName() throws Exception {
        // TODO
    }

    @Test
    @DisplayName("Получение списка всех продуктов возвращает список FoodDTO")
    void shouldReturnAllFood() throws Exception {
        // TODO
    }

    @Test
    @DisplayName("Создание нового продукта возвращает FoodDTO")
    void shouldCreateFood() throws Exception {
        // TODO
    }

    @Test
    @DisplayName("Создание уже существующего продукта выбрасывает ItemAlreadyExistsException")
    void shouldThrowExceptionWhenCreatingDuplicateFood() throws Exception {
        // TODO
    }

    @Test
    @DisplayName("Обновление существующего продукта возвращает FoodDTO")
    void shouldUpdateFood() throws Exception {
        // TODO
    }

    @Test
    @DisplayName("Обновление несуществующего продукта выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenUpdatingNonExistentFood() throws Exception {
        // TODO
    }

    @Test
    @DisplayName("Удаление несуществующего продукта выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenDeletingNonExistentFood() throws Exception {
        // TODO
    }

    @Test
    @DisplayName("Удаление продукта по валидному ID возвращает NoContent")
    void shouldDeleteFoodById() throws Exception {
        // TODO
    }
}