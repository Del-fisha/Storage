package pet.storage.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.storage.storage.dto.ElectricalDTO;
import pet.storage.storage.model.ElectricalItem;
import pet.storage.storage.repository.ElectricalRepository;
import pet.storage.storage.utility.converter.ElectricalDtoToEntityConverter;
import pet.storage.storage.utility.converter.ElectricalEntityToDtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectricalCrudService implements CrudService<ElectricalDTO> {

    private final ElectricalRepository electricalRepository;
    private final ElectricalDtoToEntityConverter dtoToEntityConverter;
    private final ElectricalEntityToDtoConverter entityToDtoConverter;

    @Autowired
    public ElectricalCrudService(ElectricalRepository repository) {
        this.electricalRepository = repository;
        this.dtoToEntityConverter = new ElectricalDtoToEntityConverter();
        this.entityToDtoConverter = new ElectricalEntityToDtoConverter();
    }


    @Override
    public ElectricalDTO findById(int id) {
        return entityToDtoConverter.convert(electricalRepository.findById(id).orElseThrow());
    }

    @Override
    public ElectricalDTO findByName(String name) {
        return entityToDtoConverter.convert(electricalRepository.findByName(name));
    }

    @Override
    public List<ElectricalDTO> findAll() {
        List<ElectricalItem> itemList = electricalRepository.findAll();
        return itemList.stream().map(entityToDtoConverter::convert).collect(Collectors.toList());
    }

    @Override
    public ElectricalDTO save(ElectricalDTO electricalDTO) {
        ElectricalItem electricalItem = dtoToEntityConverter.convert(electricalDTO);
        return entityToDtoConverter.convert(electricalRepository.save(electricalItem));
    }

    @Override
    public ElectricalDTO update(ElectricalDTO electricalDTO) {
        ElectricalItem electricalItem = electricalRepository.findByName(electricalDTO.getName());

        electricalItem.setName(electricalDTO.getName());
        electricalItem.setAmount(electricalDTO.getAmount());
        electricalItem.setFabricator(electricalDTO.getFabricator());
        electricalItem.setMetric(electricalDTO.getMetric());
        electricalItem.setDescription(electricalDTO.getDescription());
        electricalItem.setPrice(electricalDTO.getPrice());
        electricalItem.setDateOfPurchase(electricalDTO.getDateOfPurchase());
        electricalItem.setCategory(electricalDTO.getCategory());
        electricalItem.setWarrantyEndDate(electricalDTO.getWarrantyEndDate());

        return entityToDtoConverter.convert(electricalRepository.save(electricalItem));
    }

    @Override
    public void delete(int id) {
        electricalRepository.deleteById(id);
    }
}
