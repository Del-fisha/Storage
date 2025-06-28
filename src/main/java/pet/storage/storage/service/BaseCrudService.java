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
    protected final RemindKafkaService remindKafkaService;

    public BaseCrudService(final R repository, final ConverterOfEntities<T, E> converter, RemindKafkaService remindKafkaService) {
        this.repository = repository;
        this.converter = converter;
        this.remindKafkaService = remindKafkaService;
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
        System.out.println("findAll in BaseCrudService started");
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
        E savedEntity = repository.save(entity);

        remindKafkaService.sendSavedItemToReminder(savedEntity);

        return converter.convert(savedEntity);
    }

    @Override
    public T update(@Valid T dto) {
        E item = repository.findById(dto.getId()).orElseThrow(ItemNotFoundException::new);

        E updatedEntity = converter.convert(dto);
        updatedEntity.setId(item.getId());

        remindKafkaService.sendUpdatedItemToReminder(updatedEntity);

        return converter.convert(repository.save(updatedEntity));
    }

    @Override
    public void delete(int id) {
        E item = repository.findById(id).orElseThrow(ItemNotFoundException::new);

        remindKafkaService.sendRemovedItemToReminder(item);

        repository.deleteById(id);
    }
}
