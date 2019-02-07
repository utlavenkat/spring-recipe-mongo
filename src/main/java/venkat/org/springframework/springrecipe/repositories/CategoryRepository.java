package venkat.org.springframework.springrecipe.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import venkat.org.springframework.springrecipe.domain.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,String> {

    Optional<Category> findByCategoryName(String categoryName);
}
