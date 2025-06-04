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
import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;
import pet.storage.storage.service.ChemicalCrudService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ChemicalController.class)
@ContextConfiguration(classes = {ChemicalController.class, GlobalExceptionHandler.class})
class ChemicalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChemicalCrudService chemicalCrudService;

    @Autowired
    private ObjectMapper objectMapper;

    private ChemicalDTO mockChemical;

    @BeforeEach
    void setUp() {
        mockChemical = new ChemicalDTO(
                "Domestos", "Unilever", Category.Chemicals, Metric.L, 1.0, 150.0,
                LocalDate.of(2024, 5, 1), "Дезинфицирующее средство",
                LocalDate.of(2025, 5, 1)
        );
    }

    @AfterEach
    void tearDown() {
        mockChemical = null;
    }

    @Test
    @DisplayName("Получение химиката по ID возвращает ChemicalDTO")
    void shouldReturnChemicalById() throws Exception {
        when(chemicalCrudService.findById(any(Integer.class))).thenReturn(mockChemical);
        int idToFind = 1;

        mockMvc.perform(get("/storage_api/chemical/id/{id}", idToFind))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(mockChemical.getName()))
                .andExpect(jsonPath("$.fabricator").value(mockChemical.getFabricator()))
                .andExpect(jsonPath("$.amount").value(mockChemical.getAmount()));

        verify(chemicalCrudService, times(1)).findById(any(Integer.class));
    }

    @Test
    @DisplayName("Получение химиката по несуществующему ID выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenChemicalNotFoundById() throws Exception {
        when(chemicalCrudService.findById(any(Integer.class))).thenThrow(new ItemNotFoundException());
        int idToFind = 1;

        mockMvc.perform(get("/storage_api/chemical/id/{id}", idToFind))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertInstanceOf(ItemNotFoundException.class, result.getResolvedException()))
                .andExpect(result ->
                        assertEquals("Такой товар не найден",
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(chemicalCrudService, times(1)).findById(any(Integer.class));
    }

    @Test
    @DisplayName("Получение химиката по названию возвращает ChemicalDTO")
    void shouldReturnChemicalByName() throws Exception {
        when(chemicalCrudService.findByName(any(String.class))).thenReturn(mockChemical);

        String nameToFind = "Domestos";

        mockMvc.perform(get("/storage_api/chemical/name/{name}", nameToFind))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(mockChemical.getName()))
                .andExpect(jsonPath("$.fabricator").value(mockChemical.getFabricator()))
                .andExpect(jsonPath("$.amount").value(mockChemical.getAmount()));

        verify(chemicalCrudService, times(1)).findByName(any(String.class));
    }

    @Test
    @DisplayName("Получение химиката по несуществующему названию выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenChemicalNotFoundByName() throws Exception {
        when(chemicalCrudService.findByName(any(String.class))).thenThrow(new ItemNotFoundException());

        String nameToFind = "Colgate";

        mockMvc.perform(get("/storage_api/chemical/name/{name}", nameToFind))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class,
                        result.getResolvedException()))
                .andExpect(result -> assertEquals("Такой товар не найден",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(chemicalCrudService, times(1)).findByName(any(String.class));
    }

    @Test
    @DisplayName("Получение списка всех химикатов возвращает список ChemicalDTO")
    void shouldReturnAllChemicals() throws Exception {
        ChemicalDTO mockChemical1 = new ChemicalDTO("Colgate",
                "Colgate-Palmolive",
                Category.Chemicals,
                Metric.Piece,
                1.0,
                120.0,
                LocalDate.of(2024, 6, 15),
                "Зубная паста",
                LocalDate.of(2025, 6, 15));

        ChemicalDTO mockChemical2 = new ChemicalDTO("Vanish",
                "Reckitt Backbencher",
                Category.Chemicals,
                Metric.Kg,
                0.75,
                200.0,
                LocalDate.of(2024, 3, 10),
                "Пятновыводитель",
                LocalDate.of(2025, 3, 10));

        List<ChemicalDTO> chemicalDTOList = List.of(mockChemical, mockChemical2, mockChemical1);

        when(chemicalCrudService.findAll()).thenReturn(chemicalDTOList);

        mockMvc.perform(get("/storage_api/chemical/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));

        verify(chemicalCrudService, times(1)).findAll();
    }

    @Test
    @DisplayName("Создание нового химиката возвращает ChemicalDTO")
    void shouldCreateChemical() throws Exception {
        ChemicalDTO chemicalDtoToTest = new ChemicalDTO(
                "Domestos", "Unilever", Category.Chemicals, Metric.L, 1.0, 150.0,
                LocalDate.of(2024, 5, 1), "Дезинфицирующее средство",
                LocalDate.of(2025, 5, 1)
        );

        when(chemicalCrudService.save(any(ChemicalDTO.class))).thenReturn(mockChemical);

        mockMvc.perform(post("/storage_api/chemical/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chemicalDtoToTest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(mockChemical.getName()))
                .andExpect(jsonPath("$.fabricator").value(mockChemical.getFabricator()))
                .andExpect(jsonPath("$.amount").value(mockChemical.getAmount()));

        verify(chemicalCrudService, times(1)).save(any(ChemicalDTO.class));
    }

    @Test
    @DisplayName("Создание уже существующего химиката выбрасывает ItemAlreadyExistsException")
    void shouldThrowExceptionWhenCreatingDuplicateChemical() throws Exception {
        when(chemicalCrudService.save(any(ChemicalDTO.class))).thenThrow(new ItemAlreadyExistsException());

        mockMvc.perform(post("/storage_api/chemical/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockChemical)))
                .andExpect(status().isConflict())
                .andExpect(result -> assertInstanceOf(ItemAlreadyExistsException.class,
                        result.getResolvedException()))
                .andExpect(result -> assertEquals("Такая позиция уже есть на складе",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(chemicalCrudService, times(1)).save(any(ChemicalDTO.class));
    }

    @Test
    @DisplayName("Обновление существующего химиката возвращает ChemicalDTO")
    void shouldUpdateChemical() throws Exception {
        ChemicalDTO chemicalDtoToTest = new ChemicalDTO(
                "Domestos", "Unilever", Category.Chemicals, Metric.L, 3.0, 150.0,
                LocalDate.of(2024, 5, 1), "Дезинфицирующее средство",
                LocalDate.of(2025, 5, 1)
        );

        mockChemical.setAmount(chemicalDtoToTest.getAmount());
        when(chemicalCrudService.update(any(ChemicalDTO.class))).thenReturn(mockChemical);

        mockMvc.perform(put("/storage_api/chemical/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chemicalDtoToTest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(mockChemical.getName()))
                .andExpect(jsonPath("$.amount").value(mockChemical.getAmount()));

        verify(chemicalCrudService, times(1)).update(any(ChemicalDTO.class));
    }

    @Test
    @DisplayName("Обновление несуществующего химиката выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenUpdatingNonExistentChemical() throws Exception {
        ChemicalDTO dtoToTest = new ChemicalDTO("Colgate",
                "Colgate-Palmolive",
                Category.Chemicals,
                Metric.Piece,
                1.0,
                120.0,
                LocalDate.of(2024, 6, 15),
                "Зубная паста",
                LocalDate.of(2025, 6, 15));

        when(chemicalCrudService.update(any(ChemicalDTO.class))).thenThrow(new ItemNotFoundException());

        mockMvc.perform(put("/storage_api/chemical/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoToTest)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class,
                        result.getResolvedException()))
                .andExpect(result -> assertEquals("Такой товар не найден",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(chemicalCrudService, times(1)).update(any(ChemicalDTO.class));
    }

    @Test
    @DisplayName("Удаление несуществующего химиката выбрасывает ItemNotFoundException")
    void shouldThrowExceptionWhenDeletingNonExistentChemical() throws Exception {
        int nonExistentId = 999;

        doThrow(new ItemNotFoundException()).when(chemicalCrudService).delete(anyInt());

        mockMvc.perform(delete("/storage_api/chemical/{id}", nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class,
                        result.getResolvedException()))
                .andExpect(result -> assertEquals("Такой товар не найден",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(chemicalCrudService, times(1)).delete(nonExistentId);
    }

    @Test
    @DisplayName("Удаление химиката по валидному ID возвращает NoContent")
    void shouldDeleteChemicalById() throws Exception {
        int validId = 1;

        doNothing().when(chemicalCrudService).delete(validId);

        mockMvc.perform(delete("/storage_api/chemical/{id}", validId))
                .andExpect(status().isNoContent());

        verify(chemicalCrudService, times(1)).delete(validId);
    }
}