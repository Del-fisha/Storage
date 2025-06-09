package pet.storage.storage.utility.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.model.FurnitureItem;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FurnitureConverterTest {
    
    private final FurnitureConverter converter = new FurnitureConverter();

    @Test
    @DisplayName("Тест мебели без id - конвертация entity -> dto")
    public void testEntityWithoutIdToDtoConversion() {
        FurnitureItem item = new FurnitureItem(
                "Диван", "IKEA", Category.Furniture,
                Metric.Piece, 1.0, 25000.0,
                LocalDate.now(), "Описание"
        );

        FurnitureDTO dto = converter.convert(item);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
    }

    @Test
    @DisplayName("Тест мебели с id - конвертация entity -> dto")
    public void testEntityWithIdToDtoConversion() {
        FurnitureItem item = new FurnitureItem(
                "Диван", "IKEA", Category.Furniture,
                Metric.Piece, 1.0, 25000.0,
                LocalDate.now(), "Описание"
        );

        item.setId(19);

        FurnitureDTO dto = converter.convert(item);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
    }

    @Test
    @DisplayName("Тест мебели без id - конвертация dto -> entity")
    void testDtoWithoutIdToEntityConversion() {
        FurnitureDTO dto = new FurnitureDTO(
                "Диван", "IKEA", Category.Furniture,
                Metric.Piece, 1.0, 25000.0,
                LocalDate.now(), "Описание"
        );

        FurnitureItem item = converter.convert(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
    }

    @Test
    @DisplayName("Тест мебели с id - конвертация dto -> entity")
    void testDtoWithIdToEntityConversion() {
        FurnitureDTO dto = new FurnitureDTO(
                "Диван", "IKEA", Category.Furniture,
                Metric.Piece, 1.0, 25000.0,
                LocalDate.now(), "Описание"
        );

        dto.setId(19);

        FurnitureItem item = converter.convert(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
    }
}
