package pet.storage.storage.utility.converter;

import pet.storage.storage.dto.abstract_classes.ItemDTO;
import pet.storage.storage.model.abstract_classes.Item;

public interface ConverterOfEntities <T extends ItemDTO, E extends Item> {
    T convert(E entity);  // Entity -> DTO
    E convert(T dto);
}
