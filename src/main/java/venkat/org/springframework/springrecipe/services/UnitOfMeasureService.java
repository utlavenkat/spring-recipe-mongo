package venkat.org.springframework.springrecipe.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.command.UnitOfMeasureCommand;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;

public interface UnitOfMeasureService {
    Mono<UnitOfMeasureCommand> getUnitOfMeasureByUom(String uom);
    Flux<UnitOfMeasureCommand> getAllUnitOfMeasures();
    void save(UnitOfMeasure uom);
}
