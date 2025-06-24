package pet.storage.storage.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pet.storage.storage.domain.events.ProductEvent;


@Service
@RequiredArgsConstructor
public class ProductEventProducer {

    private static final String TOPIC = "product-events-topic";

    private final KafkaTemplate<Long, ProductEvent> kafkaTemplate;

    public void sendProductEvent(ProductEvent event) {
        kafkaTemplate.send(TOPIC, event.getSourceItemId(), event);
    }
}

