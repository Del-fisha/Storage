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
import pet.storage.storage.utility.converter.ChemicalConverter;

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
    private ChemicalConverter converter;

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
    @DisplayName("Успешное получение бытовой химии по ID")
    void findById_Success() {
        int expectedId = 1;
        String expectedName = "Отбеливатель";
        when(chemicalRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        when(converter.convert(any(ChemicalItem.class))).thenReturn(expectedDTO);

        ChemicalDTO actualDTO = chemicalCrudService.findById(expectedId);

        assertEquals(expectedDTO, actualDTO);
        assertEquals(expectedName, actualDTO.getName());

        verify(chemicalRepository, times(1)).findById(anyInt());
        verify(converter, times(1)).convert(any(ChemicalItem.class));
    }

    @Test
    @DisplayName("Получение бытовой химии по несуществующему ID выбрасывает исключение")
    void findById_NotFound() {
        int expectedId = -1;

        when(chemicalRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> chemicalCrudService.findById(expectedId));
    }

    @Test
    @DisplayName("Получение всех бытовой химии")
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
        when(converter.convert(expectedItem1)).thenReturn(expectedDTO1);
        when(converter.convert(expectedItem2)).thenReturn(expectedDTO2);
        when(converter.convert(mockItem)).thenReturn(expectedDTO);

        List<ChemicalDTO> actualDTOs = chemicalCrudService.findAll();

        assertEquals(expectedDTOs, actualDTOs);
        verify(chemicalRepository, times(1)).findAll();
        verify(converter, times(3)).convert(any(ChemicalItem.class));
    }

    @Test
    @DisplayName("Успешное получение бытовой химии по имени")
    void findByName_Success() {
        String expectedName = "Отбеливатель";

        when(chemicalRepository.findByName(anyString())).thenReturn(mockItem);
        when(converter.convert(any(ChemicalItem.class))).thenReturn(expectedDTO);

        ChemicalDTO actualDTO = chemicalCrudService.findByName(expectedName);

        assertEquals(expectedDTO, actualDTO);

        verify(chemicalRepository, times(1)).findByName(anyString());
        verify(converter, times(1)).convert(any(ChemicalItem.class));
    }

    @Test
    @DisplayName("Получение бытовой химии по несуществующему имени выбрасывает исключение")
    void findByName_NotFound() {
        String expectedName = "Пятновыводитель";
        when(chemicalRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> chemicalCrudService.findByName(expectedName));
        verify(chemicalRepository, times(1)).findByName(anyString());
        verify(converter, never()).convert(any(ChemicalItem.class));
    }

    @Test
    @DisplayName("Создание новой бытовой химии")
    void create_Success() {
        ChemicalDTO dto = new ChemicalDTO(
                "Отбеливатель",
                "ЧистоДом",
                Category.Chemicals,
                Metric.L,
                1.0,
                120.0,
                LocalDate.of(2025, 2, 15),
                "Отбеливатель для стирки и уборки",
                LocalDate.of(2027, 2, 15));

        when(chemicalRepository.findByName(anyString())).thenReturn(null);
        when(chemicalRepository.save(any(ChemicalItem.class))).thenReturn(mockItem);
        when(converter.convert(any(ChemicalItem.class))).thenReturn(expectedDTO);
        when(converter.convert(any(ChemicalDTO.class))).thenReturn(mockItem);

        ChemicalDTO result = chemicalCrudService.save(dto);

        assertEquals(expectedDTO, result);

        verify(chemicalRepository, times(1)).findByName(anyString());
        verify(chemicalRepository, times(1)).save(any(ChemicalItem.class));
        verify(converter, times(1)).convert(any(ChemicalItem.class));
        verify(converter, times(1)).convert(any(ChemicalDTO.class));
    }

    @Test
    @DisplayName("Создание уже существующей бытовой химии выбрасывает исключение")
    void create_Already_Exists() {
        ChemicalDTO dto = new ChemicalDTO(
                "Отбеливатель",
                "ЧистоДом",
                Category.Chemicals,
                Metric.L,
                1.0,
                120.0,
                LocalDate.of(2025, 2, 15),
                "Отбеливатель для стирки и уборки",
                LocalDate.of(2027, 2, 15));

        when(chemicalRepository.findByName(anyString())).thenReturn(mockItem);

        assertThrows(ItemAlreadyExistsException.class, () -> chemicalCrudService.save(dto));

        verify(chemicalRepository, times(1)).findByName(anyString());
        verify(chemicalRepository, never()).save(any(ChemicalItem.class));
    }

    @Test
    @DisplayName("Изменение бытовой химии")
    void update_Success() {
        // Arrange
        ChemicalDTO dtoToUpdate = new ChemicalDTO(
                "Отбеливатель",
                "ЧистоДом",
                Category.Chemicals,
                Metric.L,
                4.0,
                120.0,
                LocalDate.of(2025, 2, 15),
                "Отбеливатель для стирки и уборки",
                LocalDate.of(2027, 2, 15));

        ChemicalItem updatedEntity = new ChemicalItem();
        updatedEntity.setAmount(mockItem.getAmount() + 3.0);

        when(converter.convert(any(ChemicalDTO.class))).thenReturn(updatedEntity);
        when(converter.convert(any(ChemicalItem.class))).thenReturn(expectedDTO);

        when(chemicalRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        when(chemicalRepository.save(any(ChemicalItem.class))).thenReturn(updatedEntity);

        ChemicalDTO result = chemicalCrudService.update(dtoToUpdate);


        assertEquals(expectedDTO, result);

        verify(chemicalRepository).findById(anyInt());
        verify(chemicalRepository).save(any(ChemicalItem.class));
        verify(converter, times(1)).convert(any(ChemicalDTO.class));
        verify(converter, times(1)).convert(any(ChemicalItem.class));
    }

    @Test
    @DisplayName("Изменение несуществующей бытовой химии выбрасывает исключение")
    void update_NotFound() {
        ChemicalDTO dtoToUpdate = new ChemicalDTO(
                "Отбеливатель",
                "ЧистоДом",
                Category.Chemicals,
                Metric.L,
                4.0,
                120.0,
                LocalDate.of(2025, 2, 15),
                "Отбеливатель для стирки и уборки",
                LocalDate.of(2027, 2, 15));

        when(chemicalRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> chemicalCrudService.update(dtoToUpdate));

        verify(chemicalRepository, times(1)).findById(anyInt());
        verify(chemicalRepository, never()).save(any(ChemicalItem.class));
    }

    @Test
    @DisplayName("Удаление бытовой химии")
    void delete_Success() {
        int idToDelete = 1;

        when(chemicalRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        doNothing().when(chemicalRepository).deleteById(anyInt());

        chemicalCrudService.delete(idToDelete);

        verify(chemicalRepository, times(1)).deleteById(anyInt());
        verify(chemicalRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Удаление несуществующей бытовой химии выбрасывает исключение")
    void delete_NotFound() {
        int idToDelete = -1;

        when(chemicalRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> chemicalCrudService.delete(idToDelete));

        verify(chemicalRepository, times(1)).findById(anyInt());
        verify(chemicalRepository, never()).save(any(ChemicalItem.class));
    }
}
