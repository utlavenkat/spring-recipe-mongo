package venkat.org.springframework.springrecipe.mappers;

import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import venkat.org.springframework.springrecipe.command.IngredientCommand;
import venkat.org.springframework.springrecipe.command.UnitOfMeasureCommand;
import venkat.org.springframework.springrecipe.domain.Ingredient;
import venkat.org.springframework.springrecipe.domain.Recipe;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IngredientMapperTest {

    private IngredientMapper ingredientMapper;

    @Before
    public void setUp() {
        ingredientMapper = new IngredientMapper();
    }

    @After
    public void tearDown() {
        ingredientMapper = null;
    }

    @Test
    public void convertCommandToDomain() {
        val unitOfMeasureCommand = UnitOfMeasureCommand.builder().id(1L).uom("Cup").build();
        val ingredientCommand = IngredientCommand.builder().id(1L).description("Test Ingredient").amount(BigDecimal.TEN)
                .unitOfMeasure(unitOfMeasureCommand).recipeId(100L).build();

        val ingredient = ingredientMapper.convertCommandToDomain(ingredientCommand);
        assertNotNull(ingredient);
        assertEquals(ingredientCommand.getId(), ingredient.getId());
        assertEquals(ingredientCommand.getDescription(), ingredient.getDescription());
        assertEquals(ingredientCommand.getAmount(), ingredient.getAmount());
        assertNotNull(ingredient.getUnitOfMeasure());
        assertNotNull(ingredient.getRecipe());

        val unitOfMeasure = ingredient.getUnitOfMeasure();
        assertEquals(unitOfMeasureCommand.getId(), unitOfMeasure.getId());
        assertEquals(unitOfMeasureCommand.getUom(), unitOfMeasure.getUom());

        assertEquals(ingredientCommand.getRecipeId(), ingredient.getRecipe().getId());
    }

    @Test
    public void convertDomainToCommand() {
        val unitOfMeasure = UnitOfMeasure.builder().id(1L).uom("Cup").build();
        val recipe = Recipe.builder().id(100L).build();
        val ingredient = Ingredient.builder().description("Test Ingredient").amount(BigDecimal.TEN).id(1L)
                .unitOfMeasure(unitOfMeasure).recipe(recipe).build();
        val ingredientCommand = ingredientMapper.convertDomainToCommand(ingredient);

        assertNotNull(ingredientCommand);
        assertEquals(ingredientCommand.getAmount(), ingredient.getAmount());
        assertEquals(ingredientCommand.getDescription(), ingredient.getDescription());
        assertEquals(ingredientCommand.getId(), ingredient.getId());

        assertEquals(ingredientCommand.getRecipeId(), ingredient.getRecipe().getId());

        val unitOfMeasureCommand = ingredientCommand.getUnitOfMeasure();
        assertNotNull(unitOfMeasureCommand);
        assertEquals(unitOfMeasureCommand.getUom(), unitOfMeasure.getUom());
        assertEquals(unitOfMeasureCommand.getId(), unitOfMeasure.getId());

    }


}