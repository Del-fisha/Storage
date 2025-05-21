package pet.storage.storage.service;

import java.util.List;

public interface CrudService<T> {

    public T findById(int id);
    public T findByName(String name);
    public List<T> findAll();
    public T save(T t);
    public T update(T t);
    public void delete(int id);
}
