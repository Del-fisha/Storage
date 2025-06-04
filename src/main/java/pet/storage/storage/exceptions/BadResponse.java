package pet.storage.storage.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class BadResponse {
    private String message;
    private Map<String, String> errors;

    public BadResponse(String message) {
        this.message = message;
    }

    public BadResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "BadResponse{" +
                "message='" + message + '\'' +
                (errors != null && !errors.isEmpty() ? ", errors=" + errors : "") +
                '}';
    }
}
