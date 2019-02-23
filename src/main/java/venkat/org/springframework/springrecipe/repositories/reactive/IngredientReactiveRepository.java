package venkat.org.springframework.springrecipe.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import venkat.org.springframework.springrecipe.domain.Ingredient;

public interface IngredientReactiveRepository extends ReactiveMongoRepository<Ingredient,String> {
}
