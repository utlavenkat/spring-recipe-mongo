package venkat.org.springframework.springrecipe.repositories;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import venkat.org.springframework.springrecipe.domain.Ingredient;

import java.util.Optional;

import static org.junit.Assert.*;

@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void getIngredientById() {
        //Given
        String id = "1";

        //When
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);

        //then
        assertNotNull(ingredient);
        assertTrue(ingredient.isPresent());
        assertEquals(ingredient.get().getId(), id);
    }
}