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
public class ChemicalItem extends Item {

    @Column(name = "end_date")
    private LocalDate endDate;

    public ChemicalItem(String name, String fabricator, Category category, Metric metric, double amount,
                        double price, LocalDate dateOfPurchase, String description, LocalDate endDate) {

        super(name, fabricator, category, metric, amount, price, dateOfPurchase, description);
        this.endDate = endDate;
    }
}
