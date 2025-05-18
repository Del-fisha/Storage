package pet.storage.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import pet.storage.storage.model.abstract_classes.Item;

import java.time.LocalDate;


@Entity
public class ElectricalItem extends Item {

    @Column(name = "warranty_end_date")
    private LocalDate warrantyEndDate;
}
