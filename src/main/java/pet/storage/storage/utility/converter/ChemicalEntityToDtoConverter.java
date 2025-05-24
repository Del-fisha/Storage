package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.model.ChemicalItem;

@Component
public class ChemicalEntityToDtoConverter implements ConverterOfEntities<ChemicalDTO, ChemicalItem> {

    @Override
    public ChemicalDTO convert(ChemicalItem entity) {
        ChemicalDTO dto = new ChemicalDTO();

        dto.setName(entity.getName());
        dto.setFabricator(entity.getFabricator());
        dto.setCategory(entity.getCategory());
        dto.setMetric(entity.getMetric());
        dto.setAmount(entity.getAmount());
        dto.setPrice(entity.getPrice());
        dto.setDateOfPurchase(entity.getDateOfPurchase());
        dto.setDescription(entity.getDescription());
        dto.setEndDate(entity.getEndDate());

        return dto;
    }
}
