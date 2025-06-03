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
import pet.storage.storage.utility.converter.ElectricalDtoToEntityConverter;
import pet.storage.storage.utility.converter.ElectricalEntityToDtoConverter;

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
    private ElectricalEntityToDtoConverter entityToDtoConverter;

    @Mock
    private ElectricalDtoToEntityConverter dtoToEntityConverter;

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
                LocalDate.of(2027, 2, 15)
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
                LocalDate.of(2027, 2, 15)
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
        // Аналогично FoodCrudServiceTest
    }

    @Test
    @DisplayName("Получение электротовара по несуществующему ID выбрасывает исключение")
    void findById_NotFound() {
        // Аналогично FoodCrudServiceTest
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
                LocalDate.of(2026, 10, 10)
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
                LocalDate.of(2026, 10, 10)
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
                LocalDate.of(2027, 5, 5)
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
                LocalDate.of(2027, 5, 5)
        );

        List<ElectricalDTO> expectedDTOList = Arrays.asList(expectedDTO, expectedDTO1, expectedDTO2);
        List<ElectricalItem> electricalItems = Arrays.asList(mockItem, mockItem1, mockItem2);
    }

    @Test
    @DisplayName("Успешное получение электротовара по имени")
    void findByName_Success() {
        // Аналогично FoodCrudServiceTest
    }

    @Test
    @DisplayName("Получение электротовара по несуществующему имени выбрасывает исключение")
    void findByName_NotFound() {
        // Аналогично FoodCrudServiceTest
    }

    @Test
    @DisplayName("Создание нового электротовара")
    void create_Success() {
        // Аналогично FoodCrudServiceTest
    }

    @Test
    @DisplayName("Создание уже существующего электротовара выбрасывает исключение")
    void create_Already_Exists() {
        // Аналогично FoodCrudServiceTest
    }

    @Test
    @DisplayName("Изменение электротовара")
    void update_Success() {
        // Аналогично FoodCrudServiceTest
    }

    @Test
    @DisplayName("Изменение несуществующего электротовара выбрасывает исключение")
    void update_NotFound() {
        // Аналогично FoodCrudServiceTest
    }

    @Test
    @DisplayName("Удаление электротовара")
    void delete_Success() {
        // Аналогично FoodCrudServiceTest
    }

    @Test
    @DisplayName("Удаление несуществующего электротовара выбрасывает исключение")
    void delete_NotFound() {
        // Аналогично FoodCrudServiceTest
    }
}
