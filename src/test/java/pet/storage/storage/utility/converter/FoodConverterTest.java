package pet.storage.storage.utility.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.model.FoodItem;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodConverterTest {

    private final FoodConverter converter = new FoodConverter();

    @Test
    @DisplayName("Тест продуктов без id - конвертация entity -> dto")
    public void testEntityWithoutIdToDtoConversion() {
        FoodItem item = new FoodItem(
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

        FoodDTO dto = converter.convert(item);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
        assertEquals(item.getDateOfEaten(), dto.getDateOfEaten());
        assertEquals(item.getDateOfProduction(), dto.getDateOfProduction());
    }

    @Test
    @DisplayName("Тест продуктов с id - конвертация entity -> dto")
    public void testEntityWithIdToDtoConversion() {
        FoodItem item = new FoodItem(
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

        item.setId(19);

        FoodDTO dto = converter.convert(item);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
        assertEquals(item.getDateOfEaten(), dto.getDateOfEaten());
        assertEquals(item.getDateOfProduction(), dto.getDateOfProduction());
    }

    @Test
    @DisplayName("Тест продуктов без id - конвертация dto -> entity")
    void testDtoWithoutIdToEntityConversion() {
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

        FoodItem item = converter.convert(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
        assertEquals(item.getDateOfEaten(), dto.getDateOfEaten());
        assertEquals(item.getDateOfProduction(), dto.getDateOfProduction());
    }

    @Test
    @DisplayName("Тест продуктов с id - конвертация dto -> entity")
    void testDtoWithIdToEntityConversion() {
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

        dto.setId(19);

        FoodItem item = converter.convert(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
        assertEquals(item.getDateOfEaten(), dto.getDateOfEaten());
        assertEquals(item.getDateOfProduction(), dto.getDateOfProduction());
    }
}
