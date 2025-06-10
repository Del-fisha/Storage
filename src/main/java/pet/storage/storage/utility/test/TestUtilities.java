package pet.storage.storage.utility.test;

import org.springframework.stereotype.Component;
import pet.storage.storage.dto.abstract_classes.ItemDTO;
import pet.storage.storage.model.abstract_classes.Item;

@Component
public class TestUtilities {

     public boolean baseFieldsComparison(Item item, Item savedItem) {

         return (item.getId() == savedItem.getId()
                && item.getName().equals(savedItem.getName())
                && item.getFabricator().equals(savedItem.getFabricator())
                && item.getCategory() == savedItem.getCategory()
                && item.getMetric() == savedItem.getMetric()
                && item.getAmount() == savedItem.getAmount()
                && item.getPrice() == savedItem.getPrice()
                && item.getDateOfPurchase() == savedItem.getDateOfPurchase()
                && item.getDescription().equals(savedItem.getDescription()));
    }

    public boolean baseFieldsComparison(Item item, ItemDTO savedItem) {

        return (item.getId() == savedItem.getId()
                && item.getName().equals(savedItem.getName())
                && item.getFabricator().equals(savedItem.getFabricator())
                && item.getCategory() == savedItem.getCategory()
                && item.getMetric() == savedItem.getMetric()
                && item.getAmount() == savedItem.getAmount()
                && item.getPrice() == savedItem.getPrice()
                && item.getDateOfPurchase() == savedItem.getDateOfPurchase()
                && item.getDescription().equals(savedItem.getDescription()));
    }
}
