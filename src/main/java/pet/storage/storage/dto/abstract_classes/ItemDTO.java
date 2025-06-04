package pet.storage.storage.dto.abstract_classes;

import lombok.*;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    protected int id;
    protected String name;
    protected String fabricator;
    protected Category category;
    protected Metric metric;
    protected double amount;
    protected double price;
    protected LocalDate dateOfPurchase;
    protected String description;

    public ItemDTO(String name, String fabricator, Category category, Metric metric, double amount,
                   double price, LocalDate dateOfPurchase, String description) {
        this.name = name;
        this.fabricator = fabricator;
        this.category = category;
        this.metric = metric;
        this.amount = amount;
        this.price = price;
        this.dateOfPurchase = dateOfPurchase;
        this.description = description;
    }
}
