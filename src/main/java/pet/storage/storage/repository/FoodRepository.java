package pet.storage.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.storage.storage.model.FoodItem;

@Repository
public interface FoodRepository extends JpaRepository<FoodItem, Integer> {
    FoodItem findByName(String name);
}
