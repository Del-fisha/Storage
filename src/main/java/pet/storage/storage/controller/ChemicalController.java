package pet.storage.storage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.service.ChemicalCrudService;

import java.util.List;

@RestController("/storage_api/chemical")
public class ChemicalController {

    private final ChemicalCrudService chemicalCrudService;

    @Autowired
    public ChemicalController(ChemicalCrudService chemicalCrudService) {
        this.chemicalCrudService = chemicalCrudService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ChemicalDTO> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(chemicalCrudService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ChemicalDTO> getByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(chemicalCrudService.findByName(name));
    }

    @GetMapping("/")
    public ResponseEntity<List<ChemicalDTO>> getAll() {
        return ResponseEntity.ok(chemicalCrudService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<ChemicalDTO> create(@RequestBody ChemicalDTO chemicalDTO) {
        return ResponseEntity.ok(chemicalCrudService.save(chemicalDTO));
    }

    @PutMapping("/")
    public ResponseEntity<ChemicalDTO> update(@RequestBody ChemicalDTO chemicalDTO) {
        return ResponseEntity.ok(chemicalCrudService.update(chemicalDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        chemicalCrudService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
