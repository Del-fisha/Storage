package pet.storage.storage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.storage.storage.dto.abstract_classes.ItemDTO;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ElectricalDTO extends ItemDTO {
    private LocalDate warrantyEndDate;
}
