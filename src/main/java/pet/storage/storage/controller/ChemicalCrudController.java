package pet.storage.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.service.ChemicalCrudService;

@RestController
@RequestMapping("/storage_api/chemical")
public class ChemicalCrudController extends BaseCrudController<ChemicalDTO, ChemicalCrudService> {

    @Autowired
    public ChemicalCrudController(ChemicalCrudService service) {
        super(service);
    }
}

