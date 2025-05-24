package pet.storage.storage.utility.converter;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.ElectricalDTO;
import pet.storage.storage.model.ElectricalItem;

@Component
public class ElectricalEntityToDtoConverter implements ConverterOfEntities<ElectricalDTO, ElectricalItem> {

    @Override
    public ElectricalDTO convert(ElectricalItem entity) {
        ElectricalDTO dto = new ElectricalDTO();

        dto.setName(entity.getName());
        dto.setFabricator(entity.getFabricator());
        dto.setCategory(entity.getCategory());
        dto.setMetric(entity.getMetric());
        dto.setAmount(entity.getAmount());
        dto.setPrice(entity.getPrice());
        dto.setDateOfPurchase(entity.getDateOfPurchase());
        dto.setWarrantyEndDate(entity.getWarrantyEndDate());
        dto.setDescription(entity.getDescription());

        return dto;
    }
}
