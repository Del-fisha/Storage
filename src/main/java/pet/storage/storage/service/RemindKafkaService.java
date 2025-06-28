package pet.storage.storage.service;

import org.springframework.stereotype.Service;
import pet.storage.storage.domain.events.ProductEvent;
import pet.storage.storage.domain.events.ProductEventType;
import pet.storage.storage.kafka.producer.ProductEventProducer;
import pet.storage.storage.model.ChemicalItem;
import pet.storage.storage.model.ElectricalItem;
import pet.storage.storage.model.FoodItem;
import pet.storage.storage.model.abstract_classes.Item;

import java.time.LocalDate;

@Service
public class RemindKafkaService {

    protected final ProductEventProducer eventProducer;

    RemindKafkaService(ProductEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public void sendSavedItemToReminder(Item item) {
        ProductEvent event = sender(item);
        event.setEventType(ProductEventType.ADD);
        eventProducer.sendProductEvent(event);
    }

    public void sendUpdatedItemToReminder(Item item) {
        ProductEvent event = sender(item);
        event.setEventType(ProductEventType.UPDATE);
        eventProducer.sendProductEvent(event);
    }

    public void sendRemovedItemToReminder(Item item) {
        ProductEvent event = sender(item);
        event.setEventType(ProductEventType.DELETE);
        eventProducer.sendProductEvent(event);
    }

    public ProductEvent sender(Item item) {
        ProductEvent event = new ProductEvent();
        event.setSourceItemId((long) item.getId());
        event.setName(item.getName());


        LocalDate expirationDate = null;
        if (item instanceof FoodItem) {
            expirationDate = ((FoodItem) item).getDateOfEaten();
        } else if (item instanceof ChemicalItem) {
            expirationDate = ((ChemicalItem) item).getEndDate();
        } else if (item instanceof ElectricalItem) {
            expirationDate = ((ElectricalItem) item).getWarrantyEndDate();
        }
        event.setExpirationDate(expirationDate);
        return event;
    }
}
