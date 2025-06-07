package pet.storage.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.model.ChemicalItem;
import pet.storage.storage.repository.ChemicalRepository;
import pet.storage.storage.utility.converter.ChemicalConverter;

@Service
@Validated
public class ChemicalCrudService extends BaseCrudService <ChemicalDTO, ChemicalItem, ChemicalRepository> {

    @Autowired
    public ChemicalCrudService(ChemicalRepository repository,
                               ChemicalConverter converter) {

        super(repository, converter);
    }
}
