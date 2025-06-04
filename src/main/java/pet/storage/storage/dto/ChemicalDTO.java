package pet.storage.storage.dto;

import jakarta.validation.constraints.FutureOrPresent;
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
public class ChemicalDTO extends ItemDTO {
    @FutureOrPresent(message = "{validation.expirationDate.futureOrPresent}")
    private LocalDate endDate;

    public ChemicalDTO(String name, String fabricator, Category category, Metric metric, double amount,
                       double price, LocalDate dateOfPurchase, String description,
                       LocalDate endDate) {
        super(name, fabricator, category, metric, amount, price, dateOfPurchase, description);
        this.endDate = endDate;
    }
}
