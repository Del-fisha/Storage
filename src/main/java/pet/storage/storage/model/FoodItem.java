package pet.storage.storage.model;

import jakarta.persistence.Column;
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
public class FoodItem extends Item {

    @Column(name = "date_of_production")
    private LocalDate dateOfProduction;

    @Column(name = "date_of_eaten")
    private LocalDate dateOfEaten;

    public FoodItem(String name, String fabricator, Category category, Metric metric, double amount,
                    double price, LocalDate dateOfPurchase, String description, LocalDate dateOfProduction, LocalDate dateOfEaten) {

        super(name, fabricator, category, metric, amount, price, dateOfPurchase, description);
        this.dateOfEaten = dateOfEaten;
        this.dateOfProduction = dateOfProduction;
    }
}
