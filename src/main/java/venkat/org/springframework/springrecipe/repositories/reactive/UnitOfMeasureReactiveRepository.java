package venkat.org.springframework.springrecipe.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;

@Repository
public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure,String > {
    Mono<UnitOfMeasure> findByUom(String uom);
}
