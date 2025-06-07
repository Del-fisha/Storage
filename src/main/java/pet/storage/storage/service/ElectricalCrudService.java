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
                                 ElectricalConverter converter) {
        super(repository, converter);
    }

    @Override
    public ElectricalDTO save(ElectricalDTO dto) {
        System.out.println("Dto date of purchase : " + dto.getDateOfPurchase());
        System.out.println("Dto war months: " + dto.getWarrantyMonths());
        System.out.println("End date : " + dto.getWarrantyEndDate());
        System.out.println("Logic.....................");
        dto.setWarrantyEndDate(dto.getDateOfPurchase().plusMonths(dto.getWarrantyMonths()));
        System.out.println("Now end date is : " + dto.getWarrantyEndDate());
        System.out.println("saving");
        return super.save(dto);
    }
}
