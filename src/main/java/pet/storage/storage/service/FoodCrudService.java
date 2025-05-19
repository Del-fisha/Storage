package pet.storage.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.repository.FoodRepository;

import java.util.List;

@Service
public class FoodCrudService implements CrudService<FoodDTO> {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodCrudService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public FoodDTO findById(int id) {
        return null;
    }

    @Override
    public FoodDTO findByName(String name) {
        return null;
    }

    @Override
    public List<FoodDTO> findAll() {
        return List.of();
    }

    @Override
    public void save(FoodDTO foodDTO) {

    }

    @Override
    public void update(FoodDTO foodDTO) {

    }

    @Override
    public void delete(int id) {

    }
}
