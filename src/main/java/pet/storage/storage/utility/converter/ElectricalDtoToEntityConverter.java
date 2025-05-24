package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.ElectricalDTO;
import pet.storage.storage.model.ElectricalItem;

@Component
public class ElectricalDtoToEntityConverter implements ConverterOfEntities<ElectricalItem, ElectricalDTO> {

    @Override
    public ElectricalItem convert(ElectricalDTO dto) {
        ElectricalItem entity = new ElectricalItem();

        entity.setName(dto.getName());
        entity.setFabricator(dto.getFabricator());
        entity.setCategory(dto.getCategory());
        entity.setMetric(dto.getMetric());
        entity.setAmount(dto.getAmount());
        entity.setPrice(dto.getPrice());
        entity.setDateOfPurchase(dto.getDateOfPurchase());
        entity.setDescription(dto.getDescription());
        entity.setWarrantyEndDate(dto.getWarrantyEndDate());

        return entity;
    }
}
