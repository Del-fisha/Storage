package pet.storage.storage.exceptions;

public class ItemAlreadyExistsException extends RuntimeException {
    public ItemAlreadyExistsException() {
        super("Такая позиция уже есть на складе");
    }
}
