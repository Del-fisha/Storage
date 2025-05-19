package pet.storage.storage.utility.converter;

import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.model.FoodItem;

public class FoodDtoToEntityConverter implements ConverterOfEntities<FoodItem, FoodDTO> {

    @Override
    public FoodItem convert(FoodDTO dto) {

        FoodItem foodItem = new FoodItem();

        foodItem.setName(dto.getName());
        foodItem.setPrice(dto.getPrice());
        foodItem.setCategory(dto.getCategory());
        foodItem.setDateOfEaten(dto.getDateOfEaten());
        foodItem.setDateOfProduction(dto.getDateOfProduction());
        foodItem.setDescription(dto.getDescription());
        foodItem.setAmount(dto.getAmount());
        foodItem.setDateOfPurchase(dto.getDateOfPurchase());
        foodItem.setFabricator(dto.getFabricator());
        foodItem.setMetric(dto.getMetric());

        return foodItem;
    }
}
