package pet.storage.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.storage.storage.dto.ElectricalDTO;
import pet.storage.storage.service.ElectricalCrudService;

import java.util.List;

@RestController("/electrical")
public class ElectricalController {

    private final ElectricalCrudService electricalCrudService;

    @Autowired
    public ElectricalController(ElectricalCrudService electricalCrudService) {
        this.electricalCrudService = electricalCrudService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectricalDTO> findById(@PathVariable int id) {
        return ResponseEntity.ok(electricalCrudService.findById(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<ElectricalDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok(electricalCrudService.findByName(name));
    }

    @GetMapping("/")
    public ResponseEntity<List<ElectricalDTO>> findAll() {
        return ResponseEntity.ok(electricalCrudService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<ElectricalDTO> save(@RequestBody ElectricalDTO electricalDTO) {
        return ResponseEntity.ok(electricalCrudService.save(electricalDTO));
    }

    @PutMapping("/")
    public ResponseEntity<ElectricalDTO> update(@RequestBody ElectricalDTO electricalDTO) {
        return ResponseEntity.ok(electricalCrudService.update(electricalDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        electricalCrudService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
