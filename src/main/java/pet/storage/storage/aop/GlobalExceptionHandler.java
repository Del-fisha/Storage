package pet.storage.storage.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pet.storage.storage.exceptions.*; // Убедись, что BadResponse импортируется отсюда, а не из model.enum_classes

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<BadResponse> handleItemNotFoundException(ItemNotFoundException ex) {
        BadResponse badResponse = new BadResponse(ex.getMessage());
        return new ResponseEntity<>(badResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseEntity<BadResponse> handleItemAlreadyExistsException(ItemAlreadyExistsException ex) {
        BadResponse badResponse = new BadResponse(ex.getMessage());
        return new ResponseEntity<>(badResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IncorrectParametersException.class)
    public ResponseEntity<BadResponse> handleIncorrectParametersException(IncorrectParametersException ex) {
        BadResponse badResponse = new BadResponse(ex.getMessage());
        return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChangesAreImpossibleException.class)
    public ResponseEntity<BadResponse> handleChangesAreImpossibleException(ChangesAreImpossibleException ex) {
        BadResponse badResponse = new BadResponse(ex.getMessage());
        return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST); // Упрощено
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        BadResponse badResponse = new BadResponse("Ошибка валидации полей", errors);
        return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
    }
}