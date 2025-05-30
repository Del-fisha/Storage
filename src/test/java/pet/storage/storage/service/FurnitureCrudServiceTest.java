package pet.storage.storage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.FurnitureItem;
import pet.storage.storage.model.abstract_classes.Item;
import pet.storage.storage.model.enum_classes.*;
import pet.storage.storage.repository.FurnitureRepository;
import pet.storage.storage.utility.converter.FurnitureEntityToDtoConverter;

import java.time.LocalDate;
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

    @InjectMocks
    private FurnitureCrudService furnitureCrudService;

    @Test
    @DisplayName("Успешное получение мебели по ID")
    void findById_Success() {
        // 1. Подготовка тестовых данных
        FurnitureItem mockItem = new FurnitureItem(
                "Диван", "IKEA", Category.Furniture,
                Metric.Piece, 1.0, 25000.0,
                LocalDate.now(), "Описание"
        );
        FurnitureDTO expectedDTO = new FurnitureDTO(
                "Диван", "IKEA", Category.Furniture,
                Metric.Piece, 1.0, 25000.0,
                LocalDate.now(), "Описание"
        );

        // 2. Настройка моков
        when(furnitureRepository.findById(anyInt())).thenReturn(Optional.of(mockItem));
        when(entityToDtoConverter.convert(any(FurnitureItem.class))).thenReturn(expectedDTO);

        // 3. Вызов тестируемого метода
        FurnitureDTO result = furnitureCrudService.findById(1);

        // 4. Проверки
        assertNotNull(result);
        assertEquals("Диван", result.getName());
        verify(furnitureRepository, times(1)).findById(1);
        verify(entityToDtoConverter, times(1)).convert(any(FurnitureItem.class));
    }

    @Test
    @DisplayName("Получение мебели по несуществующему ID выбрасывает исключение")
    void findById_NotFound() {
        // 1. Настройка моков
        when(furnitureRepository.findById(999)).thenReturn(Optional.empty());

        // 2. Проверка исключения
        assertThrows(ItemNotFoundException.class, () -> {
            furnitureCrudService.findById(999);
        });

        // 3. Проверка вызова репозитория
        verify(furnitureRepository, times(1)).findById(999);
    }
}
