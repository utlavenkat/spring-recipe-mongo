package venkat.org.springframework.springrecipe.services;

import venkat.org.springframework.springrecipe.command.UnitOfMeasureCommand;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;

import java.util.Map;
import java.util.Set;

public interface UnitOfMeasureService {
    UnitOfMeasureCommand getUnitOfMeasureByUom(String uom);

    Map<String, UnitOfMeasureCommand> getUnitOfMeasureMap();

    Set<UnitOfMeasureCommand> getAllUnitOfMeasures();
    void save(UnitOfMeasure uom);
}
