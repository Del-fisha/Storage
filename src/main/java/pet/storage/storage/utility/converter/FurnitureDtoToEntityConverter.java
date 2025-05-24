package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.model.FurnitureItem;

@Component
public class FurnitureDtoToEntityConverter implements ConverterOfEntities<FurnitureItem, FurnitureDTO> {

    @Override
    public FurnitureItem convert(FurnitureDTO dto) {
        FurnitureItem entity = new FurnitureItem();

        entity.setName(dto.getName());
        entity.setFabricator(dto.getFabricator());
        entity.setCategory(dto.getCategory());
        entity.setMetric(dto.getMetric());
        entity.setAmount(dto.getAmount());
        entity.setPrice(dto.getPrice());
        entity.setDateOfPurchase(dto.getDateOfPurchase());
        entity.setDescription(dto.getDescription());

        return entity;
    }
}
