package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.model.FoodItem;

@Component
public class FoodEntityToDtoConverter implements ConverterOfEntities<FoodDTO, FoodItem> {

    @Override
    public FoodDTO convert(FoodItem entity) {
        FoodDTO dto = new FoodDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFabricator(entity.getFabricator());
        dto.setCategory(entity.getCategory());
        dto.setMetric(entity.getMetric());
        dto.setAmount(entity.getAmount());
        dto.setPrice(entity.getPrice());
        dto.setDateOfPurchase(entity.getDateOfPurchase());
        dto.setDateOfProduction(entity.getDateOfProduction());
        dto.setDateOfEaten(entity.getDateOfEaten());
        dto.setDescription(entity.getDescription());

        return dto;
    }
}
