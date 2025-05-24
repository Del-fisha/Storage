package pet.storage.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.service.FurnitureCrudService;

import java.util.List;

@RestController("/furniture")
public class FurnitureController {

    private final FurnitureCrudService furnitureCrudService;

    @Autowired
    public FurnitureController(FurnitureCrudService furnitureCrudService) {
        this.furnitureCrudService = furnitureCrudService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FurnitureDTO> getFurnitureById(@PathVariable int id) {
        return ResponseEntity.ok(furnitureCrudService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<FurnitureDTO> getFurnitureByName(@PathVariable String name) {
        return ResponseEntity.ok(furnitureCrudService.findByName(name));
    }

    @GetMapping("/")
    public ResponseEntity<List<FurnitureDTO>> getAllFurniture() {
        return ResponseEntity.ok(furnitureCrudService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<FurnitureDTO> createFurniture(@RequestBody FurnitureDTO furnitureDTO) {
        return ResponseEntity.ok(furnitureCrudService.save(furnitureDTO));
    }

    @PutMapping("/")
    public ResponseEntity<FurnitureDTO> updateFurniture(@RequestBody FurnitureDTO furnitureDTO) {
        return ResponseEntity.ok(furnitureCrudService.update(furnitureDTO));
    }

    @DeleteMapping("/id")
    public ResponseEntity<FurnitureDTO> deleteFurniture(@RequestParam int id) {
        furnitureCrudService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
