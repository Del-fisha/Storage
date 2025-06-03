package pet.storage.storage.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.ChemicalItem;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;
import pet.storage.storage.repository.ChemicalRepository;
import pet.storage.storage.utility.converter.ChemicalDtoToEntityConverter;
import pet.storage.storage.utility.converter.ChemicalEntityToDtoConverter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChemicalCrudServiceTest {

    @Mock
    private ChemicalRepository chemicalRepository;

    @Mock
    private ChemicalEntityToDtoConverter entityToDtoConverter;

    @Mock
    private ChemicalDtoToEntityConverter dtoToEntityConverter;

    @InjectMocks
    private ChemicalCrudService chemicalCrudService;

    private ChemicalItem mockItem;
    private ChemicalDTO expectedDTO;

    @BeforeEach
    void setUp() {
        mockItem = new ChemicalItem(
                "Отбеливатель",
                "ЧистоДом",
                Category.Chemicals,
                Metric.L,
                1.0,
                120.0,
                LocalDate.of(2025, 2, 15),
                "Отбеливатель для стирки и уборки",
                LocalDate.of(2027, 2, 15));

        expectedDTO = new ChemicalDTO(
                "Отбеливатель",
                "ЧистоДом",
                Category.Chemicals,
                Metric.L,
                1.0,
                120.0,
                LocalDate.of(2025, 2, 15),
                "Отбеливатель для стирки и уборки",
                LocalDate.of(2027, 2, 15)
        );
    }

    @AfterEach
    void tearDown() {
        mockItem = null;
        expectedDTO = null;
    }

    @Test
    @DisplayName("Успешное получение химиката по ID")
    void findById_Success() {
        int expectedId = 1;
        String expectedName = "Отбеливатель";
        when(chemicalRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        when(entityToDtoConverter.convert(any())).thenReturn(expectedDTO);

        ChemicalDTO actualDTO = chemicalCrudService.findById(expectedId);

        assertEquals(expectedDTO, actualDTO);
        assertEquals(expectedName, actualDTO.getName());

        verify(chemicalRepository, times(1)).findById(anyInt());
        verify(entityToDtoConverter, times(1)).convert(any());
    }

    @Test
    @DisplayName("Получение химиката по несуществующему ID выбрасывает исключение")
    void findById_NotFound() {
        int expectedId = -1;

        when(chemicalRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> chemicalCrudService.findById(expectedId));
    }

    @Test
    @DisplayName("Получение всех химикатов")
    void findAll_Success() {
        ChemicalDTO expectedDTO1 = new ChemicalDTO(
                "Средство для мытья посуды 'Лимон'",
                "FreshClean",
                Category.Chemicals,
                Metric.L,
                0.5,
                85.0,
                LocalDate.of(2024, 11, 10),
                "Жидкость для мытья посуды с ароматом лимона",
                LocalDate.of(2026, 11, 10)
        );

        ChemicalDTO expectedDTO2 = new ChemicalDTO(
                "Средство для мытья пола",
                "Уютный Дом",
                Category.Chemicals,
                Metric.L,
                1.5,
                210.0,
                LocalDate.of(2025, 4, 5),
                "Универсальное моющее средство для полов",
                LocalDate.of(2027, 4, 5)
        );

        ChemicalItem expectedItem1 = new ChemicalItem(
                "Средство для мытья посуды 'Лимон'",
                "FreshClean",
                Category.Chemicals,
                Metric.L,
                0.5,
                85.0,
                LocalDate.of(2024, 11, 10),
                "Жидкость для мытья посуды с ароматом лимона",
                LocalDate.of(2026, 11, 10)
        );

        ChemicalItem expectedItem2 = new ChemicalItem(
                "Средство для мытья пола",
                "Уютный Дом",
                Category.Chemicals,
                Metric.L,
                1.5,
                210.0,
                LocalDate.of(2025, 4, 5),
                "Универсальное моющее средство для полов",
                LocalDate.of(2027, 4, 5)
        );

        List<ChemicalDTO> expectedDTOs = Arrays.asList(expectedDTO, expectedDTO1, expectedDTO2);
        List<ChemicalItem> expectedItems = Arrays.asList(mockItem, expectedItem1, expectedItem2);

        when(chemicalRepository.findAll()).thenReturn(expectedItems);
        when(entityToDtoConverter.convert(expectedItem1)).thenReturn(expectedDTO1);
        when(entityToDtoConverter.convert(expectedItem2)).thenReturn(expectedDTO2);
        when(entityToDtoConverter.convert(mockItem)).thenReturn(expectedDTO);

        List<ChemicalDTO> actualDTOs = chemicalCrudService.findAll();

        assertEquals(expectedDTOs, actualDTOs);
        verify(chemicalRepository, times(1)).findAll();
        verify(entityToDtoConverter, times(3)).convert(any());
    }

    @Test
    @DisplayName("Успешное получение химиката по имени")
    void findByName_Success() {
        String expectedName = "Отбеливатель";

        when(chemicalRepository.findByName(anyString())).thenReturn(mockItem);
        when(entityToDtoConverter.convert(any())).thenReturn(expectedDTO);

        ChemicalDTO actualDTO = chemicalCrudService.findByName(expectedName);

        assertEquals(expectedDTO, actualDTO);

        verify(chemicalRepository, times(1)).findByName(anyString());
        verify(entityToDtoConverter, times(1)).convert(any());
    }

    @Test
    @DisplayName("Получение химиката по несуществующему имени выбрасывает исключение")
    void findByName_NotFound() {
        String expectedName = "Пятновыводитель";
        when(chemicalRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> chemicalCrudService.findByName(expectedName));
        verify(chemicalRepository, times(1)).findByName(anyString());
        verify(entityToDtoConverter, never()).convert(any());
    }

    @Test
    @DisplayName("Создание нового химиката")
    void create_Success() {
        // Аналогично FurnitureCrudServiceTest
    }

    @Test
    @DisplayName("Создание уже существующего химиката выбрасывает исключение")
    void create_Already_Exists() {
        // Аналогично FurnitureCrudServiceTest
    }

    @Test
    @DisplayName("Изменение химиката")
    void update_Success() {
        // Аналогично FurnitureCrudServiceTest
    }

    @Test
    @DisplayName("Изменение несуществующего химиката выбрасывает исключение")
    void update_NotFound() {
        // Аналогично FurnitureCrudServiceTest
    }

    @Test
    @DisplayName("Удаление химиката")
    void delete_Success() {
        // Аналогично FurnitureCrudServiceTest
    }

    @Test
    @DisplayName("Удаление несуществующего химиката выбрасывает исключение")
    void delete_NotFound() {
        // Аналогично FurnitureCrudServiceTest
    }
}
