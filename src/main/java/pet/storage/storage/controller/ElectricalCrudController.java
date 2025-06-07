package pet.storage.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.storage.storage.dto.ElectricalDTO;
import pet.storage.storage.service.ElectricalCrudService;

@RestController
@RequestMapping("/storage_api/electrical")
public class ElectricalCrudController extends BaseCrudController<ElectricalDTO, ElectricalCrudService> {

    @Autowired
    public ElectricalCrudController(ElectricalCrudService service) {
        super(service);
    }
}
