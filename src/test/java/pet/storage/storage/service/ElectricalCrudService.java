package pet.storage.storage.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pet.storage.storage.dto.ElectricalDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.ElectricalItem;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;
import pet.storage.storage.repository.ElectricalRepository;
import pet.storage.storage.utility.converter.ElectricalConverter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElectricalCrudServiceTest {

    @Mock
    private ElectricalRepository electricalRepository;

    @Mock
    private ElectricalConverter converter;

    @InjectMocks
    private ElectricalCrudService electricalCrudService;

    private ElectricalItem mockItem;
    private ElectricalDTO expectedDTO;

    @BeforeEach
    void setUp() {
        mockItem = new ElectricalItem(
                "Электрочайник",
                "Bosch",
                Category.Electrical,
                Metric.Piece,
                1,
                2100.0,
                LocalDate.of(2025, 2, 15),
                "Чайник с защитой от перегрева",
                LocalDate.of(2029, 2, 15),
                48
        );

        expectedDTO = new ElectricalDTO(
                "Электрочайник",
                "Bosch",
                Category.Electrical,
                Metric.Piece,
                1,
                2100.0,
                LocalDate.of(2025, 2, 15),
                "Чайник с защитой от перегрева",
                LocalDate.of(2029, 2, 15),
                48
        );
    }

    @AfterEach
    void tearDown() {
        mockItem = null;
        expectedDTO = null;
    }

    @Test
    @DisplayName("Успешное получение электротовара по ID")
    void findById_Success() {
        int idToFind = 1;

        when(electricalRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        when(converter.convert(any(ElectricalItem.class))).thenReturn(expectedDTO);

        ElectricalDTO actualDTO = electricalCrudService.findById(idToFind);

        assertEquals(expectedDTO, actualDTO);

        verify(electricalRepository, times(1)).findById(idToFind);
        verify(converter, times(1)).convert(any(ElectricalItem.class));
    }

    @Test
    @DisplayName("Получение электротовара по несуществующему ID выбрасывает исключение")
    void findById_NotFound() {
        int idToFind = -1;

        when(electricalRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> electricalCrudService.findById(idToFind));

        verify(electricalRepository, times(1)).findById(idToFind);
        verify(converter, never()).convert(any(ElectricalItem.class));
    }

    @Test
    @DisplayName("Получение всех электротоваров")
    void findAll_Success() {
        ElectricalItem mockItem1 = new ElectricalItem(
                "Пылесос",
                "Samsung",
                Category.Electrical,
                Metric.Piece,
                1,
                8700.0,
                LocalDate.of(2024, 10, 10),
                "Мощный пылесос для дома",
                LocalDate.of(2026, 10, 10),
                48
        );

        ElectricalDTO expectedDTO1 = new ElectricalDTO(
                "Пылесос",
                "Samsung",
                Category.Electrical,
                Metric.Piece,
                1,
                8700.0,
                LocalDate.of(2024, 10, 10),
                "Мощный пылесос для дома",
                48
        );

        ElectricalItem mockItem2 = new ElectricalItem(
                "Лампа",
                "Xiaomi",
                Category.Electrical,
                Metric.Piece,
                1,
                3200.0,
                LocalDate.of(2025, 5, 5),
                "Лампа с регулировкой яркости",
                LocalDate.of(2027, 5, 5),
                48
        );

        ElectricalDTO expectedDTO2 = new ElectricalDTO(
                "Лампа",
                "Xiaomi",
                Category.Electrical,
                Metric.Piece,
                1,
                3200.0,
                LocalDate.of(2025, 5, 5),
                "Лампа с регулировкой яркости",
                48
        );

        List<ElectricalDTO> expectedDTOList = Arrays.asList(expectedDTO, expectedDTO1, expectedDTO2);
        List<ElectricalItem> electricalItems = Arrays.asList(mockItem, mockItem1, mockItem2);

        when(electricalRepository.findAll()).thenReturn(electricalItems);
        when(converter.convert(mockItem)).thenReturn(expectedDTO);
        when(converter.convert(mockItem1)).thenReturn(expectedDTO1);
        when(converter.convert(mockItem2)).thenReturn(expectedDTO2);

        List<ElectricalDTO> actualDTOList = electricalCrudService.findAll();

        assertEquals(expectedDTOList, actualDTOList);
        verify(electricalRepository, times(1)).findAll();
        verify(converter, times(3)).convert(any(ElectricalItem.class));
    }

    @Test
    @DisplayName("Успешное получение электротовара по имени")
    void findByName_Success() {
        String nameToFind = "Электрочайник";

        when(electricalRepository.findByName(anyString())).thenReturn(mockItem);
        when(converter.convert(any(ElectricalItem.class))).thenReturn(expectedDTO);

        ElectricalDTO actualDTO = electricalCrudService.findByName(nameToFind);

        assertEquals(expectedDTO, actualDTO);

        verify(electricalRepository, times(1)).findByName(nameToFind);
        verify(converter, times(1)).convert(any(ElectricalItem.class));

    }

    @Test
    @DisplayName("Получение электротовара по несуществующему имени выбрасывает исключение")
    void findByName_NotFound() {
        String nameToFind = "Пылесос";

        when(electricalRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> electricalCrudService.findByName(nameToFind));

        verify(electricalRepository, times(1)).findByName(nameToFind);
        verify(converter, never()).convert(any(ElectricalItem.class));
    }

    @Test
    @DisplayName("Создание нового электротовара")
    void create_Success() {
        ElectricalDTO dtoToTest = new ElectricalDTO(
                "Электрочайник",
                "Bosch",
                Category.Electrical,
                Metric.Piece,
                1,
                2100.0,
                LocalDate.of(2025, 2, 15),
                "Чайник с защитой от перегрева",
                48
        );

        when(electricalRepository.findByName(anyString())).thenReturn(null);
        when(electricalRepository.save(any(ElectricalItem.class))).thenReturn(mockItem);
        when(converter.convert(any(ElectricalItem.class))).thenReturn(expectedDTO);
        when(converter.convert(any(ElectricalDTO.class))).thenReturn(mockItem);

        ElectricalDTO dto = electricalCrudService.save(dtoToTest);

        assertEquals(expectedDTO, dto);
        assertEquals(dto.getWarrantyEndDate(), dto.getDateOfPurchase().plusMonths(dtoToTest.getWarrantyMonths()));

        verify(electricalRepository, times(1)).save(any(ElectricalItem.class));
        verify(converter, times(1)).convert(any(ElectricalItem.class));
        verify(converter, times(1)).convert(any(ElectricalDTO.class));
    }

    @Test
    @DisplayName("Создание уже существующего электротовара выбрасывает исключение")
    void create_Already_Exists() {
        ElectricalDTO dtoToTest = new ElectricalDTO(
                "Электрочайник",
                "Bosch",
                Category.Electrical,
                Metric.Piece,
                1,
                2100.0,
                LocalDate.of(2025, 2, 15),
                "Чайник с защитой от перегрева",
                48
        );

        when(electricalRepository.findByName(anyString())).thenReturn(mockItem);

        assertThrows(ItemAlreadyExistsException.class, () -> electricalCrudService.save(dtoToTest));

        verify(electricalRepository, times(1)).findByName(anyString());
        verify(electricalRepository, never()).save(any(ElectricalItem.class));
        verify(converter, never()).convert(any(ElectricalDTO.class));
    }

    @Test
    @DisplayName("Изменение электротовара")
    void update_Success() {
        ElectricalDTO dtoToTest = new ElectricalDTO(
                "Электрочайник",
                "Bosch",
                Category.Electrical,
                Metric.Piece,
                4,
                2100.0,
                LocalDate.of(2025, 2, 15),
                "Чайник с защитой от перегрева",
                48
        );
        dtoToTest.setId(1);

        mockItem.setAmount(mockItem.getAmount() + 3);
        expectedDTO.setAmount(expectedDTO.getAmount() + 3);

        when(electricalRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        when(electricalRepository.save(any(ElectricalItem.class))).thenReturn(mockItem);
        when(converter.convert(any(ElectricalItem.class))).thenReturn(expectedDTO);
        when(converter.convert(any(ElectricalDTO.class))).thenReturn(mockItem);

        ElectricalDTO actualDTO = electricalCrudService.update(dtoToTest);

        assertEquals(expectedDTO, actualDTO);

        verify(electricalRepository, times(1)).findById(anyInt());
        verify(electricalRepository, times(1)).save(any(ElectricalItem.class));
        verify(converter, times(1)).convert(any(ElectricalItem.class));
        verify(converter, times(1)).convert(any(ElectricalDTO.class));
    }

    @Test
    @DisplayName("Изменение несуществующего электротовара выбрасывает исключение")
    void update_NotFound() {
        when(electricalRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> electricalCrudService.update(expectedDTO));

        verify(electricalRepository, times(1)).findById(anyInt());
        verify(electricalRepository, never()).save(any(ElectricalItem.class));
        verify(converter, never()).convert(any(ElectricalItem.class));
    }

    @Test
    @DisplayName("Удаление электротовара")
    void delete_Success() {
        int idToDelete = 1;

        when(electricalRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        doNothing().when(electricalRepository).deleteById(anyInt());

        electricalCrudService.delete(idToDelete);

        verify(electricalRepository, times(1)).deleteById(anyInt());
        verify(electricalRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Удаление несуществующего электротовара выбрасывает исключение")
    void delete_NotFound() {
        int idToDelete = -1;

        when(electricalRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> electricalCrudService.delete(idToDelete));

        verify(electricalRepository, times(1)).findById(anyInt());
        verify(electricalRepository, never()).deleteById(anyInt());
    }
}
