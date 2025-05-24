package pet.storage.storage.exceptions;

public class IncorrectParametersException extends RuntimeException {
    public IncorrectParametersException() {
        super("Некорректно введены параметры");
    }
}
