package pet.storage.storage.dto.abstract_classes;

import lombok.Getter;
import lombok.Setter;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

@Getter
@Setter
public class ItemDTO {
    protected String name;
    protected String fabricator;
    protected Category category;
    protected Metric metric;
    protected double amount;
    protected double price;
    protected LocalDate dateOfPurchase;
    protected String description;
}
