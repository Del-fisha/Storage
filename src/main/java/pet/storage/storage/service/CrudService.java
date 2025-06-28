package pet.storage.storage.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface CrudService<T> {

    public T findById(int id);
    public T findByName(String name);
    public List<T> findAll();
    public T save(@Valid T t);
    public T update(@Valid T t);
    public void delete(int id);
}
