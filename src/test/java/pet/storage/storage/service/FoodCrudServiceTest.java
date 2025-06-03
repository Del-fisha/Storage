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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        int expectedId = 1;

        when(foodRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        when(entityToDtoConverter.convert(any(FoodItem.class))).thenReturn(expectedDTO);

        FoodDTO actualDTO = foodCrudService.findById(expectedId);

        assertEquals(expectedDTO, actualDTO);

        verify(foodRepository, times(1)).findById(anyInt());
        verify(entityToDtoConverter, times(1)).convert(any(FoodItem.class));
    }

    @Test
    @DisplayName("Получение продукта по несуществующему ID выбрасывает исключение")
    void findById_NotFound() {
        int expectedId = -1;

        when(foodRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> foodCrudService.findById(expectedId));

        verify(foodRepository, times(1)).findById(anyInt());
        verify(entityToDtoConverter, never()).convert(any(FoodItem.class));
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

        when(foodRepository.findAll()).thenReturn(foodItemList);
        when(entityToDtoConverter.convert(mockItem)).thenReturn(expectedDTO);
        when(entityToDtoConverter.convert(mockItem1)).thenReturn(expectedDTO1);
        when(entityToDtoConverter.convert(mockItem2)).thenReturn(expectedDTO2);

        List<FoodDTO> actualDTOList = foodCrudService.findAll();

        assertEquals(actualDTOList, foodDTOList);
        verify(foodRepository, times(1)).findAll();
        verify(entityToDtoConverter, times(3)).convert(any(FoodItem.class));
    }

    @Test
    @DisplayName("Успешное получение продукта по имени")
    void findByName_Success() {
        String nameToFind = "Хлеб";

        when(foodRepository.findByName(anyString())).thenReturn(mockItem);
        when(entityToDtoConverter.convert(any(FoodItem.class))).thenReturn(expectedDTO);

        FoodDTO actualDTO = foodCrudService.findByName(nameToFind);

        assertEquals(expectedDTO, actualDTO);

        verify(foodRepository, times(1)).findByName(anyString());
        verify(entityToDtoConverter, times(1)).convert(any(FoodItem.class));
    }

    @Test
    @DisplayName("Получение продукта по несуществующему имени выбрасывает исключение")
    void findByName_NotFound() {
        String nameToFind = "Хлеб";

        when(foodRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> foodCrudService.findByName(nameToFind));

        verify(foodRepository, times(1)).findByName(anyString());
        verify(entityToDtoConverter, never()).convert(any(FoodItem.class));
    }

    @Test
    @DisplayName("Создание нового продукта")
    void create_Success() {
        FoodDTO dto = new FoodDTO(
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

        when(foodRepository.save(any(FoodItem.class))).thenReturn(mockItem);
        when(entityToDtoConverter.convert(any(FoodItem.class))).thenReturn(expectedDTO);
        when(dtoToEntityConverter.convert(any(FoodDTO.class))).thenReturn(mockItem);

        FoodDTO result = foodCrudService.save(dto);

        assertEquals(expectedDTO, result);

        verify(foodRepository, times(1)).save(any(FoodItem.class));
        verify(entityToDtoConverter, times(1)).convert(any(FoodItem.class));
        verify(foodRepository, times(1)).findByName(anyString());
    }

    @Test
    @DisplayName("Создание уже существующего продукта выбрасывает исключение")
    void create_Already_Exists() {
        when(foodRepository.findByName(anyString())).thenReturn(mockItem);

        assertThrows(ItemAlreadyExistsException.class, () -> foodCrudService.save(expectedDTO));

        verify(foodRepository, times(1)).findByName(anyString());
        verify(entityToDtoConverter, never()).convert(any(FoodItem.class));
        verify(foodRepository, never()).save(any(FoodItem.class));
    }

    @Test
    @DisplayName("Изменение продукта")
    void update_Success() {
        FoodDTO dto = new FoodDTO(
                "Хлеб",
                "Пекарня №1",
                Category.Food,
                Metric.Piece,
                4,
                45.0,
                LocalDate.of(2025, 6, 1),
                "Свежий ржаной хлеб",
                LocalDate.of(2025, 5, 30),
                LocalDate.of(2025, 6, 5)
        );

        mockItem.setAmount(mockItem.getAmount() + 3.0);
        expectedDTO.setAmount(expectedDTO.getAmount() + 3.0);

        when(foodRepository.findByName(anyString())).thenReturn(mockItem);
        when(foodRepository.save(any(FoodItem.class))).thenReturn(mockItem);
        when(entityToDtoConverter.convert(any(FoodItem.class))).thenReturn(expectedDTO);


        FoodDTO result = foodCrudService.update(dto);

        assertEquals(expectedDTO, result);

        verify(foodRepository, times(1)).findByName(anyString());
        verify(entityToDtoConverter, times(1)).convert(any(FoodItem.class));
        verify(foodRepository, times(1)).save(any(FoodItem.class));
    }

    @Test
    @DisplayName("Изменение несуществующего продукта выбрасывает исключение")
    void update_NotFound() {
        when(foodRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> foodCrudService.update(expectedDTO));

        verify(foodRepository, times(1)).findByName(anyString());
        verify(entityToDtoConverter, never()).convert(any(FoodItem.class));
        verify(foodRepository, never()).save(any(FoodItem.class));
    }

    @Test
    @DisplayName("Удаление продукта")
    void delete_Success() {
        int idToDelete = 1;

        when(foodRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        doNothing().when(foodRepository).deleteById(anyInt());

        foodCrudService.delete(idToDelete);

        verify(foodRepository, times(1)).findById(anyInt());
        verify(foodRepository, times(1)).deleteById(anyInt());
    }

    @Test
    @DisplayName("Удаление несуществующего продукта выбрасывает исключение")
    void delete_NotFound() {
        int idToDelete = -1;

        when(foodRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> foodCrudService.delete(idToDelete));

        verify(foodRepository, times(1)).findById(anyInt());
        verify(foodRepository, never()).deleteById(anyInt());
    }
}
