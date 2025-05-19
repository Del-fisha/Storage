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
public class ElectricalItem extends Item {

    @Column(name = "warranty_end_date")
    private LocalDate warrantyEndDate;

    public ElectricalItem(String name, String fabricator, Category category, Metric metric, double amount,
                          double price, LocalDate dateOfPurchase, String description, LocalDate warrantyEndDate) {

        super(name, fabricator, category, metric, amount, price, dateOfPurchase, description);
        this.warrantyEndDate = warrantyEndDate;
    }
}
