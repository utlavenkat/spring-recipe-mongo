package venkat.org.springframework.springrecipe.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.command.UnitOfMeasureCommand;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;
import venkat.org.springframework.springrecipe.mappers.UnitOfMeasureMapper;
import venkat.org.springframework.springrecipe.repositories.reactive.UnitOfMeasureReactiveRepository;

@Service
@AllArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final UnitOfMeasureMapper unitOfMeasureMapper = new UnitOfMeasureMapper();


    public Mono<UnitOfMeasureCommand> getUnitOfMeasureByUom(String uom) {
      return unitOfMeasureRepository.findByUom(uom).map(unitOfMeasureMapper::convertDomainToCommand);
    }

    @Override
    public Flux<UnitOfMeasureCommand> getAllUnitOfMeasures() {
        return unitOfMeasureRepository.findAll().map(unitOfMeasureMapper::convertDomainToCommand);
    }

    @Override
    public void save(UnitOfMeasure uom) {
        unitOfMeasureRepository.save(uom);
    }
}
