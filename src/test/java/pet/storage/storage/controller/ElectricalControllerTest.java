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
import pet.storage.storage.dto.ElectricalDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;
import pet.storage.storage.service.ElectricalCrudService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ElectricalController.class)
@ContextConfiguration(classes = {ElectricalController.class, GlobalExceptionHandler.class})
class ElectricalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ElectricalCrudService electricalCrudService;

    @Autowired
    private ObjectMapper objectMapper;

    ElectricalDTO mockElectrical;

    @BeforeEach
    void setUp() {
        mockElectrical = new ElectricalDTO(
                "Холодильник",
                "Bosch",
                Category.Electrical,
                Metric.Piece,
                1.0,
                64990.0,
                LocalDate.of(2023, 1, 15),
                "Встраиваемый холодильник с No Frost",
                LocalDate.of(2025, 1, 15)
        );
    }

    @AfterEach
    void tearDown() {
        mockElectrical = null;
    }

    @Test
    @DisplayName("Получение прибора по ID возвращает ElectricalDTO")
    void shouldReturnElectricalById() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Получение прибора по несуществующему ID выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenElectricalNotFoundById() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Получение прибора по названию возвращает ElectricalDTO")
    void shouldReturnElectricalByName() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Получение прибора по несуществующему названию выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenElectricalNotFoundByName() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Получение списка всех приборов возвращает список ElectricalDTO")
    void shouldReturnAllElectrical() throws Exception {
        ElectricalDTO microwave = new ElectricalDTO(
                "Микроволновка",
                "Samsung",
                Category.Electrical,
                Metric.Piece,
                1.0,
                8990.0,
                LocalDate.of(2024, 3, 10),
                "С грилем и функцией разморозки",
                LocalDate.of(2024, 7, 1)
        );

        ElectricalDTO vacuum = new ElectricalDTO(
                "Пылесос",
                "Dyson",
                Category.Electrical,
                Metric.Piece,
                1.0,
                59990.0,
                LocalDate.of(2024, 5, 20),
                "Беспроводной с лазерной подсветкой",
                LocalDate.of(2026, 5, 20)
        );

        // ToDo
    }

    @Test
    @DisplayName("Создание нового прибора возвращает ElectricalDTO")
    void shouldCreateElectrical() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Создание уже существующего прибора выбрасывает ItemAlreadyExistsException")
    void shouldThrowExceptionWhenCreatingDuplicateElectrical() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Обновление существующего прибора возвращает ElectricalDTO")
    void shouldUpdateElectrical() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Обновление несуществующего прибора выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenUpdatingNonExistentElectrical() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Удаление несуществующего прибора выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenDeletingNonExistentElectrical() throws Exception {
        // ...
    }

    @Test
    @DisplayName("Удаление прибора по валидному ID возвращает NoContent")
    void shouldDeleteElectricalById() throws Exception {
        // ...
    }
}
