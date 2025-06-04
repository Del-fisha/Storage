package pet.storage.storage.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
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
public class ElectricalDTO extends ItemDTO {

    @FutureOrPresent(message = "{validation.guaranty.futureOrPresent}")
    private LocalDate warrantyEndDate;
    @Positive
    private int warrantyMonths;

    public ElectricalDTO(String name, String fabricator, Category category, Metric metric, double amount,
                         double price, LocalDate dateOfPurchase, String description,
                         int warrantyMonths) {
        super(name, fabricator, category, metric, amount, price, dateOfPurchase, description);
        this.warrantyMonths = warrantyMonths;
        this.warrantyEndDate = dateOfPurchase.plusMonths(warrantyMonths);
    }
}
