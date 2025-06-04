package pet.storage.storage.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pet.storage.storage.dto.FurnitureDTO;
import pet.storage.storage.exceptions.ItemAlreadyExistsException;
import pet.storage.storage.exceptions.ItemNotFoundException;
import pet.storage.storage.model.FurnitureItem;
import pet.storage.storage.repository.FurnitureRepository;
import pet.storage.storage.utility.converter.FurnitureDtoToEntityConverter;
import pet.storage.storage.utility.converter.FurnitureEntityToDtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class FurnitureCrudService implements CrudService<FurnitureDTO> {

    private final FurnitureRepository furnitureRepository;
    private final FurnitureDtoToEntityConverter dtoToEntityConverter;
    private final FurnitureEntityToDtoConverter entityToDtoConverter;

    @Autowired
    public FurnitureCrudService(FurnitureRepository repository,
                                FurnitureDtoToEntityConverter dtoToEntityConverter,
                                FurnitureEntityToDtoConverter entityToDtoConverter) {
        this.furnitureRepository = repository;
        this.dtoToEntityConverter = dtoToEntityConverter;
        this.entityToDtoConverter = entityToDtoConverter;
    }


    @Override
    public FurnitureDTO findById(int id) {
        return entityToDtoConverter.convert(furnitureRepository.findById(id).orElseThrow(ItemNotFoundException::new));
    }

    @Override
    public FurnitureDTO findByName(String name) {
        FurnitureItem furnitureItem = furnitureRepository.findByName(name);
        if (furnitureItem == null) {
            throw new ItemNotFoundException();
        }
        return entityToDtoConverter.convert(furnitureItem);
    }

    @Override
    public List<FurnitureDTO> findAll() {
        List<FurnitureItem> listOfFurnitureItems = furnitureRepository.findAll();
        return listOfFurnitureItems.stream()
                .map(entityToDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public FurnitureDTO save(@Valid FurnitureDTO furnitureDTO) {
        FurnitureItem itemToFind = furnitureRepository.findByName(furnitureDTO.getName());
        if (itemToFind != null) {
            throw new ItemAlreadyExistsException();
        }

        FurnitureItem furnitureItem = dtoToEntityConverter.convert(furnitureDTO);
        return entityToDtoConverter.convert(furnitureRepository.save(furnitureItem));
    }

    @Override
    public FurnitureDTO update(@Valid FurnitureDTO furnitureDTO) {
        FurnitureItem furnitureItem = furnitureRepository.findByName(furnitureDTO.getName());
        if (furnitureItem == null) {
            throw new ItemNotFoundException();
        }

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
        FurnitureItem itemToFind = furnitureRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        furnitureRepository.deleteById(id);
    }
}
