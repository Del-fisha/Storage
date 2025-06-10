package pet.storage.storage.utility.test;

import org.springframework.stereotype.Component;
import pet.storage.storage.model.abstract_classes.Item;

@Component
public class TestUtilities {

    public TestUtilities() {
        System.out.println("***************************************************************************************");
    }
     public boolean baseFieldsComparison(Item item, Item savedItem) {

         System.out.println("Item id - " + item.getId());
         System.out.println("Saved Item id - " + savedItem.getId());
         System.out.println("Item name - " + item.getName());
         System.out.println("Saved Item name - " + savedItem.getName());
         System.out.println("Item date - " + item.getDateOfPurchase());
         System.out.println("Saved Item date - " + savedItem.getDateOfPurchase());

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
