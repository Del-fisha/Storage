package pet.storage.storage.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.storage.storage.service.CrudService;

import java.util.List;

public abstract class BaseCrudController<T, S extends CrudService<T>> {

    protected final S service;

    protected BaseCrudController(S service) {
        this.service = service;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<T> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<T> getByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/")
    public ResponseEntity<List<T>> getAll() {
        System.out.println("Get all in BaseCrudController started");
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody T dto) {
        try {
            System.out.println("--- Попытка создания DTO в контроллере: " + dto); // Этот лог должен появиться!
            return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("--- ОШИБКА В КОНТРОЛЛЕРЕ! ПРИШЛО ИСКЛЮЧЕНИЕ: " + e.getMessage()); // Этот лог должен появиться!
            e.printStackTrace(System.err); // А это - полный стек-трейс!
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Возвращаем 500
        }
    }

    @PutMapping("/")
    public ResponseEntity<T> update(@RequestBody T dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
