package pet.storage.storage.utility.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pet.storage.storage.dto.ElectricalDTO;
import pet.storage.storage.model.ElectricalItem;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElectricalConverterTest {

    private final ElectricalConverter converter = new ElectricalConverter();

    @Test
    @DisplayName("Тест электроники без id - конвертация entity -> dto")
    public void testEntityWithoutIdToDtoConversion() {
        ElectricalItem item = new ElectricalItem(
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

        ElectricalDTO dto = converter.convert(item);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
        assertEquals(item.getWarrantyEndDate(), dto.getWarrantyEndDate());
        assertEquals(item.getWarrantyMonths(), dto.getWarrantyMonths());
    }

    @Test
    @DisplayName("Тест электроники с id - конвертация entity -> dto")
    public void testEntityWithIdToDtoConversion() {
        ElectricalItem item = new ElectricalItem(
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
        item.setId(19);

        ElectricalDTO dto = converter.convert(item);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
        assertEquals(item.getWarrantyEndDate(), dto.getWarrantyEndDate());
        assertEquals(item.getWarrantyMonths(), dto.getWarrantyMonths());
    }

    @Test
    @DisplayName("Тест электроники без id - конвертация dto -> entity")
    void testDtoWithoutIdToEntityConversion() {
        ElectricalDTO dto = new ElectricalDTO(
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

        ElectricalItem item = converter.convert(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
        assertEquals(item.getWarrantyEndDate(), dto.getWarrantyEndDate());
        assertEquals(item.getWarrantyMonths(), dto.getWarrantyMonths());

    }

    @Test
    @DisplayName("Тест электроники с id - конвертация dto -> entity")
    void testDtoWithIdToEntityConversion() {
        ElectricalDTO dto = new ElectricalDTO(
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

        dto.setId(19);

        ElectricalItem item = converter.convert(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
        assertEquals(item.getWarrantyEndDate(), dto.getWarrantyEndDate());
        assertEquals(item.getWarrantyMonths(), dto.getWarrantyMonths());

    }
}
