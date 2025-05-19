package pet.storage.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.storage.storage.model.abstract_classes.Item;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ElectricalItem extends Item {

    @Column(name = "warranty_end_date")
    private LocalDate warrantyEndDate;
}
