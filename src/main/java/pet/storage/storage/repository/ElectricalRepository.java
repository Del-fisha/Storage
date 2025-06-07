package pet.storage.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.storage.storage.model.ElectricalItem;

@Repository
public interface ElectricalRepository extends BaseRepository <ElectricalItem> {
}
