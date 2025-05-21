package pet.storage.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.storage.storage.dto.ChemicalDTO;
import pet.storage.storage.model.ChemicalItem;
import pet.storage.storage.repository.ChemicalRepository;
import pet.storage.storage.utility.converter.ChemicalDtoToEntityConverter;
import pet.storage.storage.utility.converter.ChemicalEntityToDtoConverter;

import java.util.List;

@Service
public class ChemicalCrudService implements CrudService<ChemicalDTO> {

    ChemicalRepository chemicalRepository;
    ChemicalDtoToEntityConverter dtoToEntityConverter;
    ChemicalEntityToDtoConverter entityToDtoConverter;

    @Autowired
    public ChemicalCrudService(ChemicalRepository repository) {
        this.chemicalRepository = repository;
        this.dtoToEntityConverter = new ChemicalDtoToEntityConverter();
        this.entityToDtoConverter = new ChemicalEntityToDtoConverter();

    }

    @Override
    public ChemicalDTO findById(int id) {
        return entityToDtoConverter.convert(chemicalRepository.findById(id).orElseThrow());
    }

    @Override
    public ChemicalDTO findByName(String name) {
        return entityToDtoConverter.convert(chemicalRepository.findByName(name));
    }

    @Override
    public List<ChemicalDTO> findAll() {
        return chemicalRepository.findAll().stream()
                .map(entityToDtoConverter::convert)
                .toList();
    }

    @Override
    public ChemicalDTO save(ChemicalDTO chemicalDTO) {
        ChemicalItem item = dtoToEntityConverter.convert(chemicalDTO);
        return entityToDtoConverter.convert(chemicalRepository.save(item));
    }

    @Override
    public ChemicalDTO update(ChemicalDTO chemicalDTO) {
        ChemicalItem chemicalItem = new ChemicalItem();

        chemicalItem.setAmount(chemicalDTO.getAmount());
        chemicalItem.setFabricator(chemicalDTO.getFabricator());
        chemicalItem.setMetric(chemicalDTO.getMetric());
        chemicalItem.setDescription(chemicalDTO.getDescription());
        chemicalItem.setPrice(chemicalDTO.getPrice());
        chemicalItem.setDateOfPurchase(chemicalDTO.getDateOfPurchase());
        chemicalItem.setCategory(chemicalDTO.getCategory());
        chemicalItem.setEndDate(chemicalDTO.getEndDate());

        return entityToDtoConverter.convert(chemicalRepository.save(chemicalItem));
    }

    @Override
    public void delete(int id) {
        chemicalRepository.deleteById(id);
    }
}
