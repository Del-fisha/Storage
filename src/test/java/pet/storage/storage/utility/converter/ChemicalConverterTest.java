package pet.storage.storage.utility.converter;

import org.junit.jupiter.api.Test;
import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.model.ChemicalItem;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChemicalConverterTest {

    private final ChemicalConverter converter = new ChemicalConverter();

    @Test
    void testEntityToDtoConversion() {
        ChemicalItem item = new ChemicalItem(
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

        ChemicalDTO dto = converter.convert(item);

        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getFabricator(), dto.getFabricator());
        assertEquals(item.getCategory(), dto.getCategory());
        assertEquals(item.getMetric(), dto.getMetric());
        assertEquals(item.getAmount(), dto.getAmount());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getDateOfPurchase(), dto.getDateOfPurchase());
        assertEquals(item.getDescription(), dto.getDescription());
        assertEquals(item.getEndDate(), dto.getEndDate());
    }

    @Test
    void testDtoToEntityConversion() {
        ChemicalDTO dto = new ChemicalDTO(
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

        ChemicalItem item = converter.convert(dto);

        assertEquals(dto.getName(), item.getName());
        assertEquals(dto.getFabricator(), item.getFabricator());
        assertEquals(dto.getCategory(), item.getCategory());
        assertEquals(dto.getMetric(), item.getMetric());
        assertEquals(dto.getAmount(), item.getAmount());
        assertEquals(dto.getPrice(), item.getPrice());
        assertEquals(dto.getDateOfPurchase(), item.getDateOfPurchase());
        assertEquals(dto.getDescription(), item.getDescription());
        assertEquals(dto.getEndDate(), item.getEndDate());
    }
}
