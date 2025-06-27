package pet.storage.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pet.storage.storage.dto.ElectricalDTO;
import pet.storage.storage.model.ElectricalItem;
import pet.storage.storage.repository.ElectricalRepository;
import pet.storage.storage.utility.converter.ElectricalConverter;

@Service
@Validated
public class ElectricalCrudService extends BaseCrudService<ElectricalDTO, ElectricalItem, ElectricalRepository> {

    @Autowired
    public ElectricalCrudService(ElectricalRepository repository,
                                 ElectricalConverter converter,
                                 RemindKafkaService remindKafkaService) {
        super(repository, converter, remindKafkaService);
    }

    @Override
    public ElectricalDTO save(ElectricalDTO dto) {
        dto.setWarrantyEndDate(dto.getDateOfPurchase().plusMonths(dto.getWarrantyMonths()));
        return super.save(dto);
    }
}
