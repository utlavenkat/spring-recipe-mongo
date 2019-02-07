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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        Long id = 1L;
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().id(1L).uom("Teaspoon").build();
        Recipe recipe = Recipe.builder().id(100L).build();
        Ingredient ingredient = Ingredient.builder().id(1L).description("Test Ingredient").amount(BigDecimal.ONE)
                .unitOfMeasure(unitOfMeasure).recipe(recipe).build();

        //When
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));
        IngredientCommand ingredientCommand = ingredientService.findIngredientById(id);

        //Then
        assertNotNull(ingredient);
        assertEquals(ingredient.getId().longValue(), id.longValue());

    }

    @Test(expected = RuntimeException.class)
    public void findIngredientById_invalidId() {
        //Given
        Long id = 1L;

        //When
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());
        IngredientCommand ingredientCommand = ingredientService.findIngredientById(id);

        //Then
        //see @Test above for exception.
    }

    @Test
    public void save() {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().uom("Each").id(1L).build();
        Ingredient mockedIngredient = Ingredient.builder().description("Tomato").amount(BigDecimal.TEN).id(1L)
                .unitOfMeasure(unitOfMeasure).recipe(Recipe.builder().id(1L).build()).build();
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(mockedIngredient);

        IngredientCommand ingredientCommand = IngredientCommand.builder().description("Tomato").amount(BigDecimal.TEN)
                .unitOfMeasure(UnitOfMeasureCommand.builder().id(1L).uom("Each").build())
                .recipeId(1L).build();
        IngredientCommand savedIngredient = ingredientService.save(ingredientCommand);

        assertNotNull(savedIngredient);
        assertEquals(mockedIngredient.getId(), savedIngredient.getId());
        assertEquals(mockedIngredient.getDescription(), savedIngredient.getDescription());
        assertEquals(mockedIngredient.getAmount(), savedIngredient.getAmount());
    }
}