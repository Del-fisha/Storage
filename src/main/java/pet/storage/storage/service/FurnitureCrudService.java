package pet.storage.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.model.FurnitureItem;
import pet.storage.storage.repository.FurnitureRepository;
import pet.storage.storage.utility.converter.FurnitureConverter;

@Service
@Validated
public class FurnitureCrudService extends BaseCrudService<FurnitureDTO, FurnitureItem, FurnitureRepository> {

    @Autowired
    public FurnitureCrudService(FurnitureRepository repository,
                                FurnitureConverter converter) {
        super(repository, converter);
    }
}
