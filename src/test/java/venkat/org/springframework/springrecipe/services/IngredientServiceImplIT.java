package venkat.org.springframework.springrecipe.services;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import venkat.org.springframework.springrecipe.repositories.IngredientRepository;

import static org.junit.Assert.assertEquals;

@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
public class IngredientServiceImplIT {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void delete() {
        long initialSize = ingredientRepository.count();
        ingredientRepository.deleteById(1L);
        long currentSize = ingredientRepository.count();
        assertEquals(initialSize - 1, currentSize);
    }

    @Test(expected = RuntimeException.class)
    public void delete_invalidId() {
        ingredientRepository.deleteById(12345L);
    }
}