package pet.storage.storage.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.service.FoodCrudService;

import java.util.List;

@RestController
@RequestMapping("/storage_api/food")
public class FoodController {

    private final FoodCrudService foodCrudService;

    @Autowired
    public FoodController(FoodCrudService crudService) {
        this.foodCrudService = crudService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FoodDTO> getFoodById(@PathVariable("id") int id) {
        FoodDTO dto = foodCrudService.findById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<FoodDTO> getFoodByName(@PathVariable("name") String name) {
        FoodDTO dto = foodCrudService.findByName(name);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<FoodDTO>> getAllFood() {
        return new ResponseEntity<>(foodCrudService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<FoodDTO> addFood(@Valid @RequestBody FoodDTO dto) {
        return new ResponseEntity<>(foodCrudService.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<FoodDTO> updateFood(@Valid @RequestBody FoodDTO dto) {
        return new ResponseEntity<>(foodCrudService.update(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable("id") int id) {
        foodCrudService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
