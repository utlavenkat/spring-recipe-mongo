package venkat.org.springframework.springrecipe.repositories;

import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecipeRepositoryIT {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void testFindById() {
        val id = 1L;
        val recipe = recipeRepository.findById(id);
        assertNotNull(recipe);
        assertTrue(recipe.isPresent());
        assertEquals(id, recipe.get().getId().intValue());
    }

    @Test
    public void testFindById_Ivalid() {
        val recipe = recipeRepository.findById(12345L);
        assertFalse(recipe.isPresent());
    }

    @Test(expected = Exception.class)
    public void deleteById_invalid() {
        recipeRepository.deleteById(12345L);
    }

    @Test
    public void deleteById() {
        recipeRepository.deleteById(1L);
    }

}