package pet.storage.storage.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.FurnitureItem;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;
import pet.storage.storage.repository.FurnitureRepository;
import pet.storage.storage.utility.converter.FurnitureDtoToEntityConverter;
import pet.storage.storage.utility.converter.FurnitureEntityToDtoConverter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FurnitureCrudServiceTest {

    @Mock
    private FurnitureRepository furnitureRepository;

    @Mock
    private FurnitureEntityToDtoConverter entityToDtoConverter;

    @Mock
    private FurnitureDtoToEntityConverter dtoToEntityConverter;

    @InjectMocks
    private FurnitureCrudService furnitureCrudService;

    private FurnitureItem mockItem;
    private FurnitureDTO expectedDTO;


    @BeforeEach
    void setUp() {
        mockItem = new FurnitureItem(
                "Диван", "IKEA", Category.Furniture,
                Metric.Piece, 1.0, 25000.0,
                LocalDate.now(), "Описание"
        );

        expectedDTO = new FurnitureDTO(
                "Диван", "IKEA", Category.Furniture,
                Metric.Piece, 1.0, 25000.0,
                LocalDate.now(), "Описание"
        );
    }

    @AfterEach
    void tearDown() {
        mockItem = null;
        expectedDTO = null;
    }

    @Test
    @DisplayName("Успешное получение мебели по ID")
    void findById_Success() {
        int expectedId = 1;
        String expectedName = "Диван";

        when(furnitureRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        when(entityToDtoConverter.convert(any(FurnitureItem.class))).thenReturn(expectedDTO);

        FurnitureDTO result = furnitureCrudService.findById(expectedId);

        assertNotNull(result);
        assertEquals(expectedName, result.getName());

        verify(furnitureRepository, times(1)).findById(anyInt());
        verify(entityToDtoConverter, times(1)).convert(any(FurnitureItem.class));
    }

    @Test
    @DisplayName("Получение мебели по несуществующему ID выбрасывает исключение")
    void findById_NotFound() {
        int expectedId = 999;

        when(furnitureRepository.findById(anyInt())).thenReturn(Optional.empty());


        assertThrows(ItemNotFoundException.class, () -> {
            furnitureCrudService.findById(expectedId);
        });


        verify(furnitureRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Получение всех позиций мебели")
    void findAll_Success() {
        FurnitureDTO expectedDTO1 = new FurnitureDTO(
                "Стол",
                "LoftDesign",
                Category.Furniture,
                Metric.Piece,
                1.0,
                15000.0,
                LocalDate.of(2023, 5, 5),
                "Обеденный стол из массива дерева с металлическими ножками"
        );

        FurnitureDTO expectedDTO2 = new FurnitureDTO(
                "Кресло",
                "ComfortPlus",
                Category.Furniture,
                Metric.Piece,
                1.0,
                12000.0,
                LocalDate.of(2025, 1, 20),
                "Эргономичное кресло с поддержкой поясницы"
        );

        FurnitureItem mockItem1 = new FurnitureItem(
                "Стол",
                "LoftDesign",
                Category.Furniture,
                Metric.Piece,
                1.0,
                15000.0,
                LocalDate.of(2023, 5, 5),
                "Обеденный стол из массива дерева с металлическими ножками"
        );

        FurnitureItem mockItem2 = new FurnitureItem(
                "Кресло",
                "ComfortPlus",
                Category.Furniture,
                Metric.Piece,
                1.0,
                12000.0,
                LocalDate.of(2025, 1, 20),
                "Эргономичное кресло с поддержкой поясницы"
        );

        List<FurnitureDTO> expectedDTOs = Arrays.asList(expectedDTO, expectedDTO1, expectedDTO2);
        List<FurnitureItem> mockItems = Arrays.asList(mockItem, mockItem1, mockItem2);

        when(furnitureRepository.findAll()).thenReturn(mockItems);
        when(entityToDtoConverter.convert(mockItem)).thenReturn(expectedDTO);
        when(entityToDtoConverter.convert(mockItem1)).thenReturn(expectedDTO1);
        when(entityToDtoConverter.convert(mockItem2)).thenReturn(expectedDTO2);

        List<FurnitureDTO> result = furnitureCrudService.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(result, expectedDTOs);

        verify(furnitureRepository, times(1)).findAll();
        verify(entityToDtoConverter, times(3)).convert(any(FurnitureItem.class));
    }

    @Test
    @DisplayName("Успешное получение мебели по имени")
    void findByName_Success() {
        String expectedName = "Диван";

        when(furnitureRepository.findByName(anyString())).thenReturn(mockItem);
        when(entityToDtoConverter.convert(any(FurnitureItem.class))).thenReturn(expectedDTO);

        FurnitureDTO result = furnitureCrudService.findByName(expectedName);

        assertNotNull(result);
        assertEquals(expectedName, result.getName());

        verify(furnitureRepository, times(1)).findByName(anyString());
        verify(entityToDtoConverter, times(1)).convert(any(FurnitureItem.class));
    }

    @Test
    @DisplayName("Получение мебели по несуществующему имени выбрасывает исключение")
    public void findByName_NotFound() {
        String expectedName = "Стол";

        when(furnitureRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> {
            furnitureCrudService.findByName(expectedName);
        });

        verify(furnitureRepository, times(1)).findByName(anyString());
    }

    @Test
    @DisplayName("Создание новой позиции мебели")
    public void create_Success() {
        FurnitureDTO expectedDTO = new FurnitureDTO(
                "Диван", "IKEA", Category.Furniture,
                Metric.Piece, 1.0, 25000.0,
                LocalDate.now(), "Описание"
        );


        when(furnitureRepository.save(any(FurnitureItem.class))).thenReturn(mockItem);
        when(furnitureRepository.findByName(anyString())).thenReturn(null);
        when(entityToDtoConverter.convert(any(FurnitureItem.class))).thenReturn(expectedDTO);
        when(dtoToEntityConverter.convert(any(FurnitureDTO.class))).thenReturn(mockItem);

        FurnitureDTO furnitureDTO = furnitureCrudService.save(expectedDTO);

        assertNotNull(furnitureDTO);
        assertEquals(expectedDTO.getName(), furnitureDTO.getName());

        verify(furnitureRepository, times(1)).save(any(FurnitureItem.class));
        verify(furnitureRepository, times(1)).findByName(anyString());
        verify(entityToDtoConverter, times(1)).convert(any(FurnitureItem.class));
        verify(dtoToEntityConverter, times(1)).convert(any(FurnitureDTO.class));
    }

    @Test
    @DisplayName("Создание уже существующей позиции мебели выбрасывает исключение")
    public void create_Already_Exists() {
        FurnitureDTO expectedDTO = new FurnitureDTO(
                "Диван", "IKEA", Category.Furniture,
                Metric.Piece, 1.0, 25000.0,
                LocalDate.now(), "Описание"
        );

        when(furnitureRepository.findByName(anyString())).thenReturn(mockItem);

        assertThrows(ItemAlreadyExistsException.class, () -> furnitureCrudService.save(expectedDTO));

        verify(furnitureRepository, times(1)).findByName(anyString());
        verify(furnitureRepository, never()).save(any(FurnitureItem.class));
    }

    @Test
    @DisplayName("Изменение позиции мебели")
    public void update_Success() {
        FurnitureDTO dtoToTest = new FurnitureDTO(
                "Диван", "IKEA", Category.Furniture,
                Metric.Piece, 4.0, 25000.0,
                LocalDate.now(), "Описание"
        );

        mockItem.setAmount(mockItem.getAmount() + 3);

        when(furnitureRepository.save(any(FurnitureItem.class))).thenReturn(mockItem);
        when(entityToDtoConverter.convert(any(FurnitureItem.class))).thenReturn(dtoToTest);
        when(furnitureRepository.findByName(anyString())).thenReturn(mockItem);

        FurnitureDTO result = furnitureCrudService.update(dtoToTest);

        assertEquals(dtoToTest.getName(), result.getName());
        verify(furnitureRepository, times(1)).save(any(FurnitureItem.class));
        verify(furnitureRepository, times(1)).findByName(anyString());
        verify(entityToDtoConverter, times(1)).convert(any(FurnitureItem.class));
        verify(furnitureRepository, times(1)).findByName(anyString());
    }

    @Test
    @DisplayName("Изменение несуществующей позиции мебели выбрасывает исключение")
    public void update_NotFound() {
        when(furnitureRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> furnitureCrudService.update(expectedDTO));

        verify(furnitureRepository, times(1)).findByName(anyString());
        verify(furnitureRepository, never()).save(any(FurnitureItem.class));
    }

    @Test
    @DisplayName("Удаление позиции мебели")
    public void delete_Success() {
        int expectedId = 1;

        when(furnitureRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        doNothing().when(furnitureRepository).deleteById(anyInt());

        furnitureCrudService.delete(expectedId);

        verify(furnitureRepository, times(1)).findById(anyInt());
        verify(furnitureRepository, times(1)).deleteById(anyInt());
    }

    @Test
    @DisplayName("Удаление несуществующей позиции мебели выбрасывает исключение")
    public void delete_NotFound() {
        int expectedId = 999;

        when(furnitureRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> furnitureCrudService.delete(expectedId));

        verify(furnitureRepository, times(1)).findById(anyInt());
        verify(furnitureRepository, never()).deleteById(anyInt());
    }
}
