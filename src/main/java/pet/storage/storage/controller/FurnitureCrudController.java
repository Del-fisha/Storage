package pet.storage.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.service.FurnitureCrudService;

@RestController
@RequestMapping("/storage_api/furniture")
public class FurnitureCrudController extends BaseCrudController<FurnitureDTO, FurnitureCrudService> {

    @Autowired
    public FurnitureCrudController(FurnitureCrudService service) {
        super(service);
    }
}
