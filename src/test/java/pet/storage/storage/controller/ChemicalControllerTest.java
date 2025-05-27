package pet.storage.storage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;
import pet.storage.storage.service.ChemicalCrudService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChemicalController.class)
class ChemicalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChemicalCrudService chemicalCrudService; // подменяем сервис

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
    void getById_returnsChemicalDTO() throws Exception {
        // Задаем поведение мока: при вызове findById(1) сервис вернет mockChemical
        when(chemicalCrudService.findById(1)).thenReturn(mockChemical);
        when(chemicalCrudService.findById(2)).thenThrow(new ItemNotFoundException());

        // Отправляем GET-запрос к контроллеру по пути /id/1 и проверяем ответ
        mockMvc.perform(get("/id/1"))
                .andExpect(status().isOk()) // Ожидаем статус 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Ожидаем JSON в ответе
                .andExpect(jsonPath("$.name").value("Domestos")) // Проверяем поле name
                .andExpect(jsonPath("$.fabricator").value("Unilever")) // Проверяем поле fabricator
                .andExpect(jsonPath("$.amount").value(1.0)); // Проверяем поле amount


        mockMvc.perform(get("/id/2"))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertInstanceOf(ItemNotFoundException.class, result.getResolvedException()))
                .andExpect(result ->
                        assertEquals("Такой товар не найден", result.getResolvedException().getMessage()));

    }

    @Test
    void getByName() throws Exception {
        when(chemicalCrudService.findByName("Domestos")).thenReturn(mockChemical);
        when(chemicalCrudService.findByName("Unilever")).thenThrow(new ItemNotFoundException());

        mockMvc.perform(get("/name/Domestos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Domestos"))
                .andExpect(jsonPath("$.fabricator").value("Unilever"))
                .andExpect(jsonPath("$.amount").value(1.0));

        mockMvc.perform(get("/name/Unilever"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ItemNotFoundException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Такой товар не найден", result.getResolvedException().getMessage()));
    }

    @Test
    void getAll() throws Exception {
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
                "Reckitt Benckiser",
                Category.Chemicals,
                Metric.Kg,
                0.75,
                200.0,
                LocalDate.of(2024, 3, 10),
                "Пятновыводитель",
                LocalDate.of(2025, 3, 10));

        List<ChemicalDTO> chemicalDTOList = List.of(mockChemical, mockChemical2, mockChemical1);

        when(chemicalCrudService.findAll()).thenReturn(chemicalDTOList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("Domestos"))
        ;
    }

    @Test
    void create() throws Exception {
        ChemicalDTO chemicalDtoToTest = new ChemicalDTO(
                "Domestos", "Unilever", Category.Chemicals, Metric.L, 1.0, 150.0,
                LocalDate.of(2024, 5, 1), "Дезинфицирующее средство",
                LocalDate.of(2025, 5, 1)
        );

        when(chemicalCrudService.save(any(ChemicalDTO.class))).thenReturn(mockChemical);

        mockMvc.perform(post("/storage_api/chemical/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chemicalDtoToTest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Domestos"))
                .andExpect(jsonPath("$.fabricator").value("Unilever"))
                .andExpect(jsonPath("$.amount").value(1.0));

        when(chemicalCrudService.save(any(ChemicalDTO.class))).thenThrow(new ItemAlreadyExistsException());

        mockMvc.perform(post("/storage_api/chemical/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chemicalDtoToTest)))
                .andExpect(status().isConflict())
                .andExpect(result -> assertInstanceOf(ItemAlreadyExistsException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Такая позиция уже есть на складе", result.getResolvedException().getMessage()));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}