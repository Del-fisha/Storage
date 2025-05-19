package pet.storage.storage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.storage.storage.dto.abstract_classes.ItemDTO;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class FurnitureDTO extends ItemDTO {

    public FurnitureDTO(String name, String fabricator, Category category, Metric metric, double amount,
                        double price, LocalDate dateOfPurchase, String description) {
        super(name, fabricator, category, metric, amount, price, dateOfPurchase, description);
    }
}
