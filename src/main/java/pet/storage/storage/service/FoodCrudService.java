package pet.storage.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.model.FoodItem;
import pet.storage.storage.repository.FoodRepository;
import pet.storage.storage.utility.converter.FoodConverter;

@Service
@Validated
public class FoodCrudService extends BaseCrudService<FoodDTO, FoodItem, FoodRepository> {

    @Autowired
    public FoodCrudService(FoodRepository repository,
                           FoodConverter converter) {
        super(repository, converter);
    }
}
