package venkat.org.springframework.springrecipe.repositories.reactive;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import venkat.org.springframework.springrecipe.domain.Recipe;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTestIT {

    @Autowired
    private RecipeReactiveRepository recipeReactiveRepository;

    @Before
    public void setUp() {
        recipeReactiveRepository.deleteAll().block();
    }

    @After
    public void delete() {
        recipeReactiveRepository.deleteAll().block();
    }


    @Test
    public void testSaveRecipe() {
        Recipe recipe = Recipe.builder().description("Test Recipe").build();
        recipeReactiveRepository.save(recipe).block();
        Long count = recipeReactiveRepository.count().block();
        assertEquals(1, count.longValue());
    }
}