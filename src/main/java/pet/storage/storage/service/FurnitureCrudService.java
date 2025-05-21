package pet.storage.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.model.FurnitureItem;
import pet.storage.storage.repository.FurnitureRepository;
import pet.storage.storage.utility.converter.FurnitureDtoToEntityConverter;
import pet.storage.storage.utility.converter.FurnitureEntityToDtoConverter;

import java.util.List;

@Service
public class FurnitureCrudService implements CrudService<FurnitureDTO> {

    private final FurnitureRepository furnitureRepository;
    private final FurnitureDtoToEntityConverter dtoToEntityConverter;
    private final FurnitureEntityToDtoConverter entityToDtoConverter;

    @Autowired
    public FurnitureCrudService(FurnitureRepository repository) {
        this.furnitureRepository = repository;
        this.dtoToEntityConverter = new FurnitureDtoToEntityConverter();
        this.entityToDtoConverter = new FurnitureEntityToDtoConverter();
    }


    @Override
    public FurnitureDTO findById(int id) {
        return entityToDtoConverter.convert(furnitureRepository.findById(id).orElseThrow());
    }

    @Override
    public FurnitureDTO findByName(String name) {
        return entityToDtoConverter.convert(furnitureRepository.findByName(name));
    }

    @Override
    public List<FurnitureDTO> findAll() {
        List<FurnitureItem> listOfFurnitureItems = furnitureRepository.findAll();
        return listOfFurnitureItems.stream()
                .map(entityToDtoConverter::convert)
                .toList();
    }

    @Override
    public FurnitureDTO save(FurnitureDTO furnitureDTO) {
        FurnitureItem furnitureItem = dtoToEntityConverter.convert(furnitureDTO);
        return entityToDtoConverter.convert(furnitureRepository.save(furnitureItem));
    }

    @Override
    public FurnitureDTO update(FurnitureDTO furnitureDTO) {
        FurnitureItem furnitureItem = new FurnitureItem();

        furnitureItem.setName(furnitureDTO.getName());
        furnitureItem.setAmount(furnitureDTO.getAmount());
        furnitureItem.setFabricator(furnitureDTO.getFabricator());
        furnitureItem.setMetric(furnitureDTO.getMetric());
        furnitureItem.setDescription(furnitureDTO.getDescription());
        furnitureItem.setPrice(furnitureDTO.getPrice());
        furnitureItem.setDateOfPurchase(furnitureDTO.getDateOfPurchase());
        furnitureItem.setCategory(furnitureDTO.getCategory());

        return entityToDtoConverter.convert(furnitureRepository.save(furnitureItem));
    }

    @Override
    public void delete(int id) {
        furnitureRepository.deleteById(id);
    }
}
