package pet.storage.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.service.FoodCrudService;

@RestController
public class FoodController {

    private final FoodCrudService foodCrudService;

    @Autowired
    public FoodController(FoodCrudService foodRepository) {
        this.foodCrudService = foodRepository;
    }

    @GetMapping("/food/id/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable("id") int id) {
        FoodDTO dto = foodCrudService.findById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/food/name/{name}")
    public ResponseEntity<?> getFoodByName(@PathVariable("name") String name) {
        FoodDTO dto = foodCrudService.findByName(name);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/food")
    public ResponseEntity<?> getAllFood() {
        return new ResponseEntity<>(foodCrudService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/food")
    public ResponseEntity<?> addFood(@RequestBody FoodDTO dto) {
        return new ResponseEntity<>(foodCrudService.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/food")
    public ResponseEntity<?> updateFood(@RequestBody FoodDTO dto) {
        return new ResponseEntity<>(foodCrudService.update(dto), HttpStatus.OK);
    }

    @DeleteMapping("/food/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable("id") int id) {
        foodCrudService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
