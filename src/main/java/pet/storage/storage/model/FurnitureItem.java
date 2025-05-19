package pet.storage.storage.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.storage.storage.model.abstract_classes.Item;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FurnitureItem extends Item {
}
