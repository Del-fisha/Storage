package pet.storage.storage.utility.converter;

import pet.storage.storage.dto.abstract_classes.ItemDTO;
import pet.storage.storage.model.abstract_classes.Item;

public abstract class BaseConverter<T extends ItemDTO, E extends Item>
        implements ConverterOfEntities<T, E> {

    @Override
    public T convert(E entity) {
        if (entity == null) return null;

        T dto = createDto();
        mapCommonFieldsFromEntity(entity, dto);
        mapSpecificFieldsFromEntity(entity, dto);
        return dto;
    }

    @Override
    public E convert(T dto) {
        if (dto == null) return null;

        E entity = createEntity();
        mapCommonFieldsFromDto(dto, entity);
        mapSpecificFieldsFromDto(dto, entity);
        return entity;
    }

    // Абстрактные методы для создания объектов
    protected abstract T createDto();
    protected abstract E createEntity();


    protected void mapCommonFieldsFromEntity(E entity, T dto) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFabricator(entity.getFabricator());
        dto.setCategory(entity.getCategory());
        dto.setMetric(entity.getMetric());
        dto.setAmount(entity.getAmount());
        dto.setPrice(entity.getPrice());
        dto.setDateOfPurchase(entity.getDateOfPurchase());
        dto.setDescription(entity.getDescription());

    }

    protected void mapCommonFieldsFromDto(T dto, E entity) {
        if (dto.getId() != 0) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setFabricator(dto.getFabricator());
        entity.setCategory(dto.getCategory());
        entity.setMetric(dto.getMetric());
        entity.setAmount(dto.getAmount());
        entity.setPrice(dto.getPrice());
        entity.setDateOfPurchase(dto.getDateOfPurchase());
        entity.setDescription(dto.getDescription());
    }

    // Уникальные поля должны реализовывать наследники
    protected abstract void mapSpecificFieldsFromEntity(E entity, T dto);
    protected abstract void mapSpecificFieldsFromDto(T dto, E entity);
}
