package venkat.org.springframework.springrecipe.repositories;

import org.springframework.data.repository.CrudRepository;
import venkat.org.springframework.springrecipe.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
