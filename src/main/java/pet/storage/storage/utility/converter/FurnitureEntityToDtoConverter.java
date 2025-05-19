package pet.storage.storage.utility.converter;

import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.model.FurnitureItem;

public class FurnitureEntityToDtoConverter implements ConverterOfEntities<FurnitureDTO, FurnitureItem> {

    @Override
    public FurnitureDTO convert(FurnitureItem entity) {
        FurnitureDTO dto = new FurnitureDTO();

        dto.setName(entity.getName());
        dto.setFabricator(entity.getFabricator());
        dto.setCategory(entity.getCategory());
        dto.setMetric(entity.getMetric());
        dto.setAmount(entity.getAmount());
        dto.setPrice(entity.getPrice());
        dto.setDateOfPurchase(entity.getDateOfPurchase());
        dto.setDescription(entity.getDescription());

        return null;
    }
}
