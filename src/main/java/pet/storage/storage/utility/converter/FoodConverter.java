package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.model.FoodItem;

@Component
public class FoodConverter extends BaseConverter <FoodDTO, FoodItem> {

    @Override
    protected FoodDTO createDto() {
        return new FoodDTO();
    }

    @Override
    protected FoodItem createEntity() {
        return new FoodItem();
    }

    @Override
    protected void mapSpecificFieldsFromEntity(FoodItem entity, FoodDTO dto) {
        dto.setDateOfProduction(entity.getDateOfProduction());
        dto.setDateOfEaten(entity.getDateOfEaten());
    }

    @Override
    protected void mapSpecificFieldsFromDto(FoodDTO dto, FoodItem entity) {
        entity.setDateOfProduction(dto.getDateOfProduction());
        entity.setDateOfEaten(dto.getDateOfEaten());
    }
}
