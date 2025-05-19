package pet.storage.storage.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.storage.storage.model.abstract_classes.Item;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class FurnitureItem extends Item {

    public FurnitureItem(String name, String fabricator, Category category, Metric metric, double amount,
                         double price, LocalDate dateOfPurchase, String description) {
        super(name, fabricator, category, metric, amount, price, dateOfPurchase, description);

    }
}
