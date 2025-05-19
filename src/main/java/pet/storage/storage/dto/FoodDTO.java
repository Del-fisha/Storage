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
public class FoodDTO extends ItemDTO {

    private LocalDate dateOfProduction;

    private LocalDate dateOfEaten;

    public FoodDTO(String name, String description, int price, LocalDate dateOfProduction, LocalDate dateOfEaten,
                    String fabricator, Category category, Metric metric, double amount, LocalDate dateOfPurchase) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dateOfProduction = dateOfProduction;
        this.dateOfEaten = dateOfEaten;
        this.fabricator = fabricator;
        this.category = category;
        this.metric = metric;
        this.amount = amount;
        this.dateOfPurchase = dateOfPurchase;
    }
}
