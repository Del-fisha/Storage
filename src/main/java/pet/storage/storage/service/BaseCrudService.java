package pet.storage.storage.service;

import jakarta.validation.Valid;
import pet.storage.storage.domain.events.ProductEvent;
import pet.storage.storage.domain.events.ProductEventType;
import pet.storage.storage.dto.abstract_classes.ItemDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.kafka.producer.ProductEventProducer;
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
    protected final ProductEventProducer eventProducer;

    protected BaseCrudService(
            R repository,
            ConverterOfEntities<T, E> converter, ProductEventProducer eventProducer) {

        this.repository = repository;
        this.converter = converter;
        this.eventProducer = eventProducer;
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
        E savedEntity = repository.save(entity); // СОХРАНИМ РЕЗУЛЬТАТ SAVE В ПЕРЕМЕННУЮ

        // --- Отправка события ADD в Kafka ---
        ProductEvent event = new ProductEvent();
        event.setSourceItemId((long) savedEntity.getId()); // Преобразуем int в Long
        event.setName(savedEntity.getName()); // Предполагается, что у E есть getName()
        // Предполагается, что у E есть getExpirationDate()
        // Если E (Item) не имеет getExpirationDate(), это будет проблемой.
        // Нужно, чтобы твои Item (FoodItem, ChemicalItem и т.д.) его имели.
        if (savedEntity instanceof ExpirableItem) { // Если у тебя есть интерфейс/абстрактный класс для сроков годности
            event.setExpirationDate(((ExpirableItem) savedEntity).getExpirationDate());
        } else {
            // Если у тебя нет expirationDate для всех Item, реши, что делать:
            // 1. Игнорировать для таких Item (expirationDate будет null в ProductEvent)
            // 2. Выбрасывать ошибку, если Item должен быть Expirable
            // 3. Установить дефолтное значение
            // Для начала, можно оставить null.
            event.setExpirationDate(null); // Или другую логику
        }
        event.setEventType(ProductEventType.ADD);
        // userId уже по умолчанию 1L в ProductEvent, если ты его так настроил

        eventProducer.sendProductEvent(event); // ОТПРАВКА!
        // --- Конец отправки события ADD ---

        return converter.convert(savedEntity);
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
