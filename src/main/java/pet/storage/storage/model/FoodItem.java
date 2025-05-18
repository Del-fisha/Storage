package pet.storage.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pet.storage.storage.model.abstract_classes.Item;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class FoodItem extends Item {

    @Column(name = "date_of_production")
    private LocalDate dateOfProduction;

    @Column(name = "date_of_eaten")
    private LocalDate dateOfEaten;
}
