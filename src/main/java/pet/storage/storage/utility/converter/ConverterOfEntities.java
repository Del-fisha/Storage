package pet.storage.storage.utility.converter;

public interface ConverterOfEntities <T, E> {
    T convert(E entity);
}
