package venkat.org.springframework.springrecipe.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import venkat.org.springframework.springrecipe.domain.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe,Long> {
}
