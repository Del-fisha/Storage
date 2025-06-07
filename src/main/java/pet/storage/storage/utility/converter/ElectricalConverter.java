package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.ElectricalDTO;
import pet.storage.storage.model.ElectricalItem;

@Component
public class ElectricalConverter extends BaseConverter <ElectricalDTO, ElectricalItem> {

    @Override
    protected ElectricalDTO createDto() {
        return new ElectricalDTO();
    }

    @Override
    protected ElectricalItem createEntity() {
        return new ElectricalItem();
    }

    @Override
    protected void mapSpecificFieldsFromEntity(ElectricalItem entity, ElectricalDTO dto) {
        dto.setWarrantyEndDate(entity.getWarrantyEndDate());
        dto.setWarrantyMonths(entity.getWarrantyMonths());
    }

    @Override
    protected void mapSpecificFieldsFromDto(ElectricalDTO dto, ElectricalItem entity) {
        entity.setWarrantyEndDate(dto.getWarrantyEndDate());
        entity.setWarrantyMonths(dto.getWarrantyMonths());
    }
}
