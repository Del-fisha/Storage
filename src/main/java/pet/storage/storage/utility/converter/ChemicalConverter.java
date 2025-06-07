package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.model.ChemicalItem;

@Component
public class ChemicalConverter extends BaseConverter<ChemicalDTO, ChemicalItem> {

    @Override
    protected ChemicalDTO createDto() {
        return new ChemicalDTO();
    }

    @Override
    protected ChemicalItem createEntity() {
        return new ChemicalItem();
    }

    @Override
    protected void mapSpecificFieldsFromEntity(ChemicalItem entity, ChemicalDTO dto) {
        dto.setEndDate(entity.getEndDate());
    }

    @Override
    protected void mapSpecificFieldsFromDto(ChemicalDTO dto, ChemicalItem entity) {
        entity.setEndDate(dto.getEndDate());
    }
}
