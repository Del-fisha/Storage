package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.model.FoodItem;

@Component
public class FoodDtoToEntityConverter implements ConverterOfEntities<FoodItem, FoodDTO> {

    @Override
    public FoodItem convert(FoodDTO dto) {

        FoodItem entity = new FoodItem();

        if (dto.getId() != 0) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setCategory(dto.getCategory());
        entity.setDateOfEaten(dto.getDateOfEaten());
        entity.setDateOfProduction(dto.getDateOfProduction());
        entity.setDescription(dto.getDescription());
        entity.setAmount(dto.getAmount());
        entity.setDateOfPurchase(dto.getDateOfPurchase());
        entity.setFabricator(dto.getFabricator());
        entity.setMetric(dto.getMetric());

        return entity;
    }
}
