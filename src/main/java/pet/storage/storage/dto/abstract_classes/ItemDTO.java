package pet.storage.storage.dto.abstract_classes;

import jakarta.validation.constraints.*;
import lombok.*;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public abstract class ItemDTO {
    protected int id;

    @NotBlank(message = "{validation.name.notBlank}")
    protected String name;

    @NotBlank(message = "{validation.fabricator.notBlank}")
    protected String fabricator;

    @NotNull(message = "{validation.category.notNull}")
    protected Category category;

    @NotNull(message = "{validation.metric.notNull}")
    protected Metric metric;

    @Positive(message = "{validation.amount.positive}")
    protected double amount;

    @PositiveOrZero(message = "{validation.price.positiveOrZero}")
    protected double price;

    @PastOrPresent(message = "{validation.dateOfPurchase.pastOrPresent}")
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
