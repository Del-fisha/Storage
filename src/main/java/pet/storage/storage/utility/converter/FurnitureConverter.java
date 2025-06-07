package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.model.FurnitureItem;

@Component
public class FurnitureConverter extends BaseConverter <FurnitureDTO, FurnitureItem> {

    @Override
    protected FurnitureDTO createDto() {
        return new FurnitureDTO();
    }

    @Override
    protected FurnitureItem createEntity() {
        return new FurnitureItem();
    }

    @Override
    protected void mapSpecificFieldsFromEntity(FurnitureItem entity, FurnitureDTO dto) {

    }

    @Override
    protected void mapSpecificFieldsFromDto(FurnitureDTO dto, FurnitureItem entity) {

    }
}
