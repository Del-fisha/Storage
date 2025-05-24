package pet.storage.storage.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadResponse {

    String message;
    public BadResponse(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "\nmessage - " + message;
    }

}
