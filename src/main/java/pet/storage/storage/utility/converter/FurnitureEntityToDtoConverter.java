package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.model.FurnitureItem;

@Component
public class FurnitureEntityToDtoConverter implements ConverterOfEntities<FurnitureDTO, FurnitureItem> {

    @Override
    public FurnitureDTO convert(FurnitureItem entity) {
        FurnitureDTO dto = new FurnitureDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFabricator(entity.getFabricator());
        dto.setCategory(entity.getCategory());
        dto.setMetric(entity.getMetric());
        dto.setAmount(entity.getAmount());
        dto.setPrice(entity.getPrice());
        dto.setDateOfPurchase(entity.getDateOfPurchase());
        dto.setDescription(entity.getDescription());

        return dto;
    }
}
