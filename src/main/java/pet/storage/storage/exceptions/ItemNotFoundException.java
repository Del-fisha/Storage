package pet.storage.storage.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super("Такой товар не найден");
    }
}
