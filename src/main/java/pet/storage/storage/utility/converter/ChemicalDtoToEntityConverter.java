package pet.storage.storage.utility.converter;

import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.model.ChemicalItem;

public class ChemicalDtoToEntityConverter implements ConverterOfEntities<ChemicalItem, ChemicalDTO> {

    @Override
    public ChemicalItem convert(ChemicalDTO dto) {
        ChemicalItem entity = new ChemicalItem();

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
