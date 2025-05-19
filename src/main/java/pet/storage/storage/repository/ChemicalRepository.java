package pet.storage.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.storage.storage.model.ChemicalItem;

@Repository
public interface ChemicalRepository extends JpaRepository<ChemicalItem, Integer> {
}
