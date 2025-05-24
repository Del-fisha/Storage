package pet.storage.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.storage.storage.dto.FoodDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.FoodItem;
import pet.storage.storage.repository.FoodRepository;
import pet.storage.storage.utility.converter.FoodDtoToEntityConverter;
import pet.storage.storage.utility.converter.FoodEntityToDtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodCrudService implements CrudService<FoodDTO> {

    private final FoodRepository foodRepository;
    private final FoodEntityToDtoConverter foodEntityToDtoConverter;
    private final FoodDtoToEntityConverter foodDtoToEntityConverter;

    @Autowired
    public FoodCrudService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
        this.foodEntityToDtoConverter = new FoodEntityToDtoConverter();
        this.foodDtoToEntityConverter = new FoodDtoToEntityConverter();
    }

    @Override
    public FoodDTO findById(int id) {
        return foodEntityToDtoConverter.convert(foodRepository.findById(id).orElseThrow(ItemNotFoundException::new));
    }

    @Override
    public FoodDTO findByName(String name) {
        FoodItem foodItem = foodRepository.findByName(name);
        if (foodItem == null) {
            throw new ItemNotFoundException();
        }
        return foodEntityToDtoConverter.convert(foodItem);
    }

    @Override
    public List<FoodDTO> findAll() {
        List<FoodItem> foodDTOList = foodRepository.findAll();
        return foodDTOList.stream().map(foodEntityToDtoConverter::convert).collect(Collectors.toList());
    }

    @Override
    public FoodDTO save(FoodDTO foodDTO) {
        FoodItem itemToFind = foodRepository.findByName(foodDTO.getName());
        if (itemToFind != null) {
            throw new ItemAlreadyExistsException();
        }
        FoodItem foodDto = foodDtoToEntityConverter.convert(foodDTO);
        return foodEntityToDtoConverter.convert(foodRepository.save(foodDto));
    }

    @Override
    public FoodDTO update(FoodDTO foodDTO) {
        FoodItem foodItem = foodRepository.findByName(foodDTO.getName());
        if (foodItem == null) {
            throw new ItemNotFoundException();
        }

        foodItem.setAmount(foodDTO.getAmount());
        foodItem.setFabricator(foodDTO.getFabricator());
        foodItem.setMetric(foodDTO.getMetric());
        foodItem.setDescription(foodDTO.getDescription());
        foodItem.setPrice(foodDTO.getPrice());
        foodItem.setDateOfPurchase(foodDTO.getDateOfPurchase());
        foodItem.setCategory(foodDTO.getCategory());
        foodItem.setDateOfProduction(foodDTO.getDateOfProduction());
        foodItem.setDateOfEaten(foodDTO.getDateOfEaten());

        return foodEntityToDtoConverter.convert(foodRepository.save(foodItem));
    }

    @Override
    public void delete(int id) {
        FoodItem foodItem = foodRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        foodRepository.deleteById(id);
    }
}
