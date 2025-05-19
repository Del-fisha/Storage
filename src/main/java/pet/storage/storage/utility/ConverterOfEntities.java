package pet.storage.storage.utility;

public interface ConverterOfEntities <T, E> {
    T convert(E entity);
}
