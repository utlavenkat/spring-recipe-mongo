package venkat.org.springframework.springrecipe.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import venkat.org.springframework.springrecipe.command.UnitOfMeasureCommand;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;
import venkat.org.springframework.springrecipe.mappers.UnitOfMeasureMapper;
import venkat.org.springframework.springrecipe.repositories.UnitOfMeasureRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureMapper unitOfMeasureMapper = new UnitOfMeasureMapper();

    @Transactional
    public UnitOfMeasureCommand getUnitOfMeasureByUom(String uom) {
        UnitOfMeasureCommand unitOfMeasureCommand = null;
        Optional<UnitOfMeasure> savedUnitOfMeasure = unitOfMeasureRepository.findByUom(uom);
        if (savedUnitOfMeasure.isPresent()) {
            unitOfMeasureCommand = unitOfMeasureMapper.convertDomainToCommand(savedUnitOfMeasure.get());
        }
        return unitOfMeasureCommand;
    }

    @Transactional
    public Map<String, UnitOfMeasureCommand> getUnitOfMeasureMap() {
        Map<String, UnitOfMeasureCommand> unitOfMeasureMap = new HashMap<>();

        unitOfMeasureRepository.findAll().forEach(unitOfMeasure -> unitOfMeasureMap.put(unitOfMeasure.getUom(),
                unitOfMeasureMapper.convertDomainToCommand(unitOfMeasure)));

        return unitOfMeasureMap;

    }

    @Override
    @Transactional
    public Set<UnitOfMeasureCommand> getAllUnitOfMeasures() {
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        unitOfMeasureRepository.findAll().forEach(unitOfMeasure -> unitOfMeasureCommands.add(unitOfMeasureMapper
                .convertDomainToCommand(unitOfMeasure)));
        return unitOfMeasureCommands;
    }
}
