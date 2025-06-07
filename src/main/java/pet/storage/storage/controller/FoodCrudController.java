package pet.storage.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.service.FoodCrudService;

@RestController
@RequestMapping("/storage_api/food")
public class FoodCrudController extends BaseCrudController<FoodDTO, FoodCrudService> {

    @Autowired
    public FoodCrudController(FoodCrudService service) {
        super(service);
    }
}
