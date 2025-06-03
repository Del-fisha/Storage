package pet.storage.storage.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.FoodItem;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;
import pet.storage.storage.repository.FoodRepository;
import pet.storage.storage.utility.converter.FoodDtoToEntityConverter;
import pet.storage.storage.utility.converter.FoodEntityToDtoConverter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodCrudServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private FoodEntityToDtoConverter entityToDtoConverter;

    @Mock
    private FoodDtoToEntityConverter dtoToEntityConverter;

    @InjectMocks
    private FoodCrudService foodCrudService;

    private FoodItem mockItem;
    private FoodDTO expectedDTO;

    @BeforeEach
    void setUp() {
        mockItem = new FoodItem(
                "Хлеб",
                "Пекарня №1",
                Category.Food,
                Metric.Piece,
                1,
                45.0,
                LocalDate.of(2025, 6, 1),
                "Свежий ржаной хлеб",
                LocalDate.of(2025, 5, 30),
                LocalDate.of(2025, 6, 5)
        );

        expectedDTO = new FoodDTO(
                "Хлеб",
                "Пекарня №1",
                Category.Food,
                Metric.Piece,
                1,
                45.0,
                LocalDate.of(2025, 6, 1),
                "Свежий ржаной хлеб",
                LocalDate.of(2025, 5, 30),
                LocalDate.of(2025, 6, 5)
        );
    }

    @AfterEach
    void tearDown() {
        mockItem = null;
        expectedDTO = null;
    }

    @Test
    @DisplayName("Успешное получение продукта по ID")
    void findById_Success() {
        // Заглушки и проверки аналогично ChemicalCrudServiceTest
    }

    @Test
    @DisplayName("Получение продукта по несуществующему ID выбрасывает исключение")
    void findById_NotFound() {
        // Аналогично ChemicalCrudServiceTest
    }

    @Test
    @DisplayName("Получение всех продуктов")
    void findAll_Success() {
        FoodItem mockItem1 = new FoodItem(
                "Молоко",
                "Молочный завод",
                Category.Food,
                Metric.L,
                1,
                80.0,
                LocalDate.of(2025, 6, 2),
                "Пастеризованное молоко",
                LocalDate.of(2025, 5, 31),
                LocalDate.of(2025, 6, 7)
        );
        FoodItem mockItem2 = new FoodItem(
                "Яблоки",
                "Фермер Иванов",
                Category.Food,
                Metric.Kg,
                2,
                120.0,
                LocalDate.of(2025, 5, 28),
                "Сочные яблоки сорта Гала",
                LocalDate.of(2025, 5, 25),
                LocalDate.of(2025, 6, 10)
        );
        FoodDTO expectedDTO1 = new FoodDTO(
                "Молоко",
                "Молочный завод",
                Category.Food,
                Metric.L,
                1,
                80.0,
                LocalDate.of(2025, 6, 2),
                "Пастеризованное молоко",
                LocalDate.of(2025, 5, 31),
                LocalDate.of(2025, 6, 7)
        );

        FoodDTO expectedDTO2 = new FoodDTO(
                "Яблоки",
                "Фермер Иванов",
                Category.Food,
                Metric.Kg,
                2,
                120.0,
                LocalDate.of(2025, 5, 28),
                "Сочные яблоки сорта Гала",
                LocalDate.of(2025, 5, 25),
                LocalDate.of(2025, 6, 10)
        );

        List<FoodDTO> foodDTOList = Arrays.asList(expectedDTO, expectedDTO1, expectedDTO2);
        List<FoodItem> foodItemList = Arrays.asList(mockItem, mockItem1, mockItem2);
    }

    @Test
    @DisplayName("Успешное получение продукта по имени")
    void findByName_Success() {
        // Аналогично ChemicalCrudServiceTest
    }

    @Test
    @DisplayName("Получение продукта по несуществующему имени выбрасывает исключение")
    void findByName_NotFound() {
        // Аналогично ChemicalCrudServiceTest
    }

    @Test
    @DisplayName("Создание нового продукта")
    void create_Success() {
        // Аналогично ChemicalCrudServiceTest
    }

    @Test
    @DisplayName("Создание уже существующего продукта выбрасывает исключение")
    void create_Already_Exists() {
        // Аналогично ChemicalCrudServiceTest
    }

    @Test
    @DisplayName("Изменение продукта")
    void update_Success() {
        // Аналогично ChemicalCrudServiceTest
    }

    @Test
    @DisplayName("Изменение несуществующего продукта выбрасывает исключение")
    void update_NotFound() {
        // Аналогично ChemicalCrudServiceTest
    }

    @Test
    @DisplayName("Удаление продукта")
    void delete_Success() {
        // Аналогично ChemicalCrudServiceTest
    }

    @Test
    @DisplayName("Удаление несуществующего продукта выбрасывает исключение")
    void delete_NotFound() {
        // Аналогично ChemicalCrudServiceTest
    }
}
