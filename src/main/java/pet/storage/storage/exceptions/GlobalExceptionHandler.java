package pet.storage.storage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<BadResponse> handleItemNotFoundException(ItemNotFoundException ex) {
        BadResponse badResponse = new BadResponse(ex.getMessage());
        return new ResponseEntity<>(badResponse, HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseEntity<BadResponse> handleItemAlreadyExistsException(ItemAlreadyExistsException ex) {
        BadResponse badResponse = new BadResponse(ex.getMessage());
        return new ResponseEntity<>(badResponse, HttpStatus.valueOf(HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(IncorrectParametersException.class)
    public ResponseEntity<BadResponse> handleItemNotFoundException(IncorrectParametersException ex) {
        BadResponse badResponse = new BadResponse(ex.getMessage());
        return new ResponseEntity<>(badResponse, HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ChangesAreImpossibleException.class)
    public ResponseEntity<BadResponse> handleItemNotFoundException(ChangesAreImpossibleException ex) {
        BadResponse badResponse = new BadResponse(ex.getMessage());
        return new ResponseEntity<>(badResponse, HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
}
