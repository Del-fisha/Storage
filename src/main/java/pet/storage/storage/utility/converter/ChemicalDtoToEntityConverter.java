package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.model.ChemicalItem;

@Component
public class ChemicalDtoToEntityConverter implements ConverterOfEntities<ChemicalItem, ChemicalDTO> {

    @Override
    public ChemicalItem convert(ChemicalDTO dto) {
        ChemicalItem entity = new ChemicalItem();

        if (dto.getId() != 0) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setFabricator(dto.getFabricator());
        entity.setCategory(dto.getCategory());
        entity.setMetric(dto.getMetric());
        entity.setAmount(dto.getAmount());
        entity.setPrice(dto.getPrice());
        entity.setDateOfPurchase(dto.getDateOfPurchase());
        entity.setDescription(dto.getDescription());
        entity.setEndDate(dto.getEndDate());

        return entity;
    }
}
