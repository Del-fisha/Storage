package pet.storage.storage.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
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

    @PastOrPresent
    private LocalDate dateOfProduction;

    @FutureOrPresent(message = "{validation.expirationDate.futureOrPresent}")
    private LocalDate dateOfEaten;

    public FoodDTO(String name, String fabricator, Category category, Metric metric, double amount,
                   double price, LocalDate dateOfPurchase, String description,
                   LocalDate dateOfProduction, LocalDate dateOfEaten) {
        super(name, fabricator, category, metric, amount, price, dateOfPurchase, description);
        this.dateOfProduction = dateOfProduction;
        this.dateOfEaten = dateOfEaten;
    }
}
