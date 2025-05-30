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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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
        when(electricalCrudService.findById(anyInt())).thenReturn(mockElectrical);
        int idToGet = 1;

        mockMvc.perform(get("/storage_api/electrical/id/{id}", idToGet))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(mockElectrical.getName()))
                .andExpect(jsonPath("$.price").value(mockElectrical.getPrice()));

        verify(electricalCrudService, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Получение прибора по несуществующему ID выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenElectricalNotFoundById() throws Exception {
        int idToGet = 1;

        when(electricalCrudService.findById(anyInt())).thenThrow(new ItemNotFoundException());

        mockMvc.perform(get("/storage_api/electrical/id/{id}", idToGet))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class,
                        result.getResolvedException()))
                .andExpect(jsonPath("$.message").value("Такой товар не найден"));

        verify(electricalCrudService, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Получение прибора по названию возвращает ElectricalDTO")
    void shouldReturnElectricalByName() throws Exception {
        String nameToFind = "Холодильник";

        when(electricalCrudService.findByName(anyString())).thenReturn(mockElectrical);

        mockMvc.perform(get("/storage_api/electrical/name/{name}", nameToFind))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(mockElectrical.getName()))
                .andExpect(jsonPath("$.price").value(mockElectrical.getPrice()));

        verify(electricalCrudService, times(1)).findByName(anyString());
    }

    @Test
    @DisplayName("Получение прибора по несуществующему названию выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenElectricalNotFoundByName() throws Exception {
        String nameToFind = "Микроволновка";

        when(electricalCrudService.findByName(anyString())).thenThrow(new ItemNotFoundException());

        mockMvc.perform(get("/storage_api/electrical/name/{name}", nameToFind))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Такой товар не найден"))
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class,
                        result.getResolvedException()));

        verify(electricalCrudService, times(1)).findByName(anyString());
    }

    @Test
    @DisplayName("Получение списка всех приборов возвращает список ElectricalDTO")
    void shouldReturnAllElectrical() throws Exception {
        ElectricalDTO mockElectrical1 = new ElectricalDTO(
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

        ElectricalDTO mockElectrical2 = new ElectricalDTO(
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

        List<ElectricalDTO> electricalDTOList = List.of(mockElectrical, mockElectrical1, mockElectrical2);

        when(electricalCrudService.findAll()).thenReturn(electricalDTOList);

        mockMvc.perform(get("/storage_api/electrical/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(electricalDTOList.size()))
                .andExpect(jsonPath("$[0].name").value(mockElectrical.getName()))
                .andExpect(jsonPath("$[1].name").value(mockElectrical1.getName()))
                .andExpect(jsonPath("$[2].name").value(mockElectrical2.getName()));
    }

    @Test
    @DisplayName("Создание нового прибора возвращает ElectricalDTO")
    void shouldCreateElectrical() throws Exception {
        ElectricalDTO dtoToTest = new ElectricalDTO(
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

        when(electricalCrudService.save(any(ElectricalDTO.class))).thenReturn(mockElectrical);

        mockMvc.perform(post("/storage_api/electrical/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoToTest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(dtoToTest.getName()))
                .andExpect(jsonPath("$.price").value(dtoToTest.getPrice()));

        verify(electricalCrudService, times(1)).save(any(ElectricalDTO.class));

    }

    @Test
    @DisplayName("Создание уже существующего прибора выбрасывает ItemAlreadyExistsException")
    void shouldThrowExceptionWhenCreatingDuplicateElectrical() throws Exception {
        when(electricalCrudService.save(any(ElectricalDTO.class))).thenThrow(new ItemAlreadyExistsException());

        mockMvc.perform(post("/storage_api/electrical/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockElectrical)))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Такая позиция уже есть на складе"))
                .andExpect(result -> assertInstanceOf(ItemAlreadyExistsException.class,
                        result.getResolvedException()));

        verify(electricalCrudService, times(1)).save(any(ElectricalDTO.class));
    }

    @Test
    @DisplayName("Обновление существующего прибора возвращает ElectricalDTO")
    void shouldUpdateElectrical() throws Exception {
        int plusAmount = 3;
        ElectricalDTO dtoToTest = new ElectricalDTO(
                "Холодильник",
                "Bosch",
                Category.Electrical,
                Metric.Piece,
                4.0,
                64990.0,
                LocalDate.of(2023, 1, 15),
                "Встраиваемый холодильник с No Frost",
                LocalDate.of(2025, 1, 15)
        );

        mockElectrical.setAmount(mockElectrical.getAmount() + plusAmount);

        when(electricalCrudService.update(any(ElectricalDTO.class))).thenReturn(mockElectrical);
        mockMvc.perform(put("/storage_api/electrical/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoToTest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(dtoToTest.getName()))
                .andExpect(jsonPath("$.price").value(dtoToTest.getPrice()))
                .andExpect(jsonPath("$.amount").value(mockElectrical.getAmount()));

        verify(electricalCrudService, times(1)).update(any(ElectricalDTO.class));

    }

    @Test
    @DisplayName("Обновление несуществующего прибора выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenUpdatingNonExistentElectrical() throws Exception {
        ElectricalDTO mockElectrical1 = new ElectricalDTO(
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

        when(electricalCrudService.update(any(ElectricalDTO.class))).thenThrow(new ItemNotFoundException());

        mockMvc.perform(put("/storage_api/electrical/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockElectrical1)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Такой товар не найден"))
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class,
                        result.getResolvedException()));

        verify(electricalCrudService, times(1)).update(any(ElectricalDTO.class));
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
