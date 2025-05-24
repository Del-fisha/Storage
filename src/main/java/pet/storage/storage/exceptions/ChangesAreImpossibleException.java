package pet.storage.storage.exceptions;

public class ChangesAreImpossibleException extends RuntimeException {
    public ChangesAreImpossibleException() {
        super("Такие изменения невозможны");
    }
}
