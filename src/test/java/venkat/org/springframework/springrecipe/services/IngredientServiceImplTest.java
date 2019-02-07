package venkat.org.springframework.springrecipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import venkat.org.springframework.springrecipe.command.IngredientCommand;
import venkat.org.springframework.springrecipe.command.UnitOfMeasureCommand;
import venkat.org.springframework.springrecipe.domain.Ingredient;
import venkat.org.springframework.springrecipe.domain.Recipe;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;
import venkat.org.springframework.springrecipe.repositories.IngredientRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    private IngredientService ingredientService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientRepository);
    }

    @Test
    public void findIngredientById() {
        //Given
        String id = "1";
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().id("1").uom("Teaspoon").build();
        Recipe recipe = Recipe.builder().id("100").build();
        Ingredient ingredient = Ingredient.builder().id("1").description("Test Ingredient").amount(BigDecimal.ONE)
                .unitOfMeasure(unitOfMeasure).build();

        //When
        when(ingredientRepository.findById(anyString())).thenReturn(Optional.of(ingredient));

        //Then
        assertNotNull(ingredient);
        assertEquals(ingredient.getId(), id);

    }

    @Test(expected = RuntimeException.class)
    public void findIngredientById_invalidId() {
        //Given
        String id = "1";

        //When
        when(ingredientRepository.findById(anyString())).thenReturn(Optional.empty());
        IngredientCommand ingredientCommand = ingredientService.findIngredientById(id);

        //Then
        //see @Test above for exception.
    }

    @Test
    public void save() {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().uom("Each").id("1").build();
        Ingredient mockedIngredient = Ingredient.builder().description("Tomato").amount(BigDecimal.TEN).id("1")
                .unitOfMeasure(unitOfMeasure).build();
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(mockedIngredient);

        IngredientCommand ingredientCommand = IngredientCommand.builder().description("Tomato").amount(BigDecimal.TEN)
                .unitOfMeasure(UnitOfMeasureCommand.builder().id("1").uom("Each").build())
                .build();
        IngredientCommand savedIngredient = ingredientService.save(ingredientCommand);

        assertNotNull(savedIngredient);
        assertEquals(mockedIngredient.getId(), savedIngredient.getId());
        assertEquals(mockedIngredient.getDescription(), savedIngredient.getDescription());
        assertEquals(mockedIngredient.getAmount(), savedIngredient.getAmount());
    }
}