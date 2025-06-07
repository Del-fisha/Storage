package pet.storage.storage.service;

import jakarta.validation.Valid;
import pet.storage.storage.dto.abstract_classes.ItemDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.abstract_classes.Item;
import pet.storage.storage.repository.BaseRepository;
import pet.storage.storage.utility.converter.ConverterOfEntities;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseCrudService
        <T extends ItemDTO,
                E extends Item,
                R extends BaseRepository<E>> implements CrudService<T> {

    protected final R repository;
    protected final ConverterOfEntities<T, E> converter;

    protected BaseCrudService(
            R repository,
            ConverterOfEntities<T, E> converter) {

        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public T findById(int id) {
        return converter.convert(repository.findById(id).orElseThrow(ItemNotFoundException::new));
    }

    @Override
    public T findByName(String name) {
        E item = repository.findByName(name);
        if (item == null) {
            throw new ItemNotFoundException();
        }

        return converter.convert(item);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll().stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public T save(@Valid T dto) {
        E item = repository.findByName(dto.getName());
        if (item != null) {
            throw new ItemAlreadyExistsException();
        }

        E entity = converter.convert(dto);
        return converter.convert(repository.save(entity));
    }

    @Override
    public T update(@Valid T dto) {
        E item = repository.findById(dto.getId()).orElseThrow(ItemNotFoundException::new);

        E updatedEntity = converter.convert(dto);
        updatedEntity.setId(item.getId());
        return converter.convert(repository.save(updatedEntity));
    }

    @Override
    public void delete(int id) {
        E item = repository.findById(id).orElseThrow(ItemNotFoundException::new);
        repository.deleteById(id);
    }
}
