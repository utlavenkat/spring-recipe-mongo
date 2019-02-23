package venkat.org.springframework.springrecipe.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.domain.Category;

@Repository
public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category,String> {

    Mono<Category> findByCategoryName(String categoryName);

}
