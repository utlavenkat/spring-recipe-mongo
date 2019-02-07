package venkat.org.springframework.springrecipe.mappers;

import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import venkat.org.springframework.springrecipe.command.*;
import venkat.org.springframework.springrecipe.domain.Difficulty;
import venkat.org.springframework.springrecipe.domain.Recipe;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.Assert.*;

public class RecipeMapperTest {
    private RecipeMapper recipeMapper;

    @Before
    public void setUp() {
        recipeMapper = new RecipeMapper();
    }

    @After
    public void tearDown() {
        recipeMapper = null;
    }

    @Test
    public void convertCommandToDomain() {
        val unitOfMeasureCommand = UnitOfMeasureCommand.builder().id(1L).uom("Cup").build();
        val recipeCommand = RecipeCommand.builder().id(1L).cookTime(10).description("Test Recipe")
                .difficulty(DifficultyCommand.EASY).directions("1.2.3.4").prepTime(20).servings(4).source("My Source")
                .url("http://my.recipe.com").categories(new HashSet<>()).ingredients(new HashSet<>()).build();
        recipeCommand.addCategory(CategoryCommand.builder().categoryName("Category 1").id(1L).build());
        recipeCommand.addCategory(CategoryCommand.builder().categoryName("Category 2").id(2L).build());

        recipeCommand.addIngredient(IngredientCommand.builder().id(1L).amount(BigDecimal.ONE).description("Test Ingredient1").unitOfMeasure(unitOfMeasureCommand).build());
        recipeCommand.addIngredient(IngredientCommand.builder().id(2L).amount(BigDecimal.ONE).description("Test Ingredient2").unitOfMeasure(unitOfMeasureCommand).build());

        NotesCommand notesCommand = NotesCommand.builder().id(1L).notes("Test Notes").recipe(recipeCommand).build();
        recipeCommand.setNotes(notesCommand);

        val recipe = recipeMapper.convertCommandToDomain(recipeCommand);
        assertNotNull(recipe);
        assertEquals(recipeCommand.getCookTime(), recipe.getCookTime());
        assertEquals(recipeCommand.getDescription(), recipe.getDescription());
        assertEquals(recipeCommand.getDifficulty().name(), recipe.getDifficulty().name());
        assertEquals(recipeCommand.getDirections(), recipe.getDirections());
        assertEquals(recipeCommand.getId(), recipe.getId());
        assertEquals(recipeCommand.getPrepTime(), recipe.getPrepTime());
        assertEquals(recipeCommand.getServings(), recipe.getServings());
        assertEquals(recipeCommand.getSource(), recipe.getSource());
        assertEquals(recipeCommand.getUrl(), recipe.getUrl());
        assertNotNull(recipe.getIngredients());
        assertEquals(recipeCommand.getIngredients().size(), recipe.getIngredients().size());
        assertEquals(recipeCommand.getCategories().size(), recipe.getCategories().size());
    }

    @Test
    public void convertDomainToCommand() {
        val recipe = Recipe.builder().id(1L).description("Test Recipe").cookTime(10).difficulty(Difficulty.EASY)
                .directions("1.2.3.4").prepTime(10).servings(4).source("Test Source")
                .url("http://test.url.com").ingredients(new HashSet<>()).categories(new HashSet<>()).build();
        val recipeCommand = recipeMapper.convertDomainToCommand(recipe);
        assertNotNull(recipeCommand);

        assertEquals(recipeCommand.getUrl(), recipe.getUrl());
        assertEquals(recipeCommand.getSource(), recipe.getSource());
        assertEquals(recipeCommand.getServings(), recipe.getServings());
        assertEquals(recipeCommand.getPrepTime(), recipe.getPrepTime());
        assertEquals(recipeCommand.getId(), recipe.getId());
        assertEquals(recipeCommand.getDirections(), recipe.getDirections());
        assertEquals(recipeCommand.getDifficulty().name(), recipe.getDifficulty().name());
        assertEquals(recipeCommand.getDescription(), recipe.getDescription());
        assertEquals(recipeCommand.getCookTime(), recipe.getCookTime());
        assertNull(recipeCommand.getIngredients());
        assertNull(recipeCommand.getCategories());
    }
}