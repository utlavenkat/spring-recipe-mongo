package venkat.org.springframework.springrecipe.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;

import java.util.Optional;

@Repository
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure,String> {
    Optional<UnitOfMeasure> findByUom(String uom);
}
