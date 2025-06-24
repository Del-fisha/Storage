package pet.storage.storage.domain.events;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductEvent {
    private Long sourceItemId;
    private String name;
    private LocalDate expirationDate;
    private Long userId = 1L;
    private ProductEventType eventType;
}
