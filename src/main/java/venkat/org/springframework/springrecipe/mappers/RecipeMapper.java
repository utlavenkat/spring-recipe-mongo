package venkat.org.springframework.springrecipe.mappers;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;
import venkat.org.springframework.springrecipe.command.CategoryCommand;
import venkat.org.springframework.springrecipe.command.DifficultyCommand;
import venkat.org.springframework.springrecipe.command.IngredientCommand;
import venkat.org.springframework.springrecipe.command.RecipeCommand;
import venkat.org.springframework.springrecipe.domain.Category;
import venkat.org.springframework.springrecipe.domain.Difficulty;
import venkat.org.springframework.springrecipe.domain.Ingredient;
import venkat.org.springframework.springrecipe.domain.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class RecipeMapper {
    private IngredientMapper ingredientMapper;
    private NotesMapper notesMapper;
    private CategoryMapper categoryMapper;

    public RecipeMapper() {
        ingredientMapper = new IngredientMapper();
        notesMapper = new NotesMapper();
        categoryMapper = new CategoryMapper();
    }

    public Recipe convertCommandToDomain(final RecipeCommand recipeCommand) {
        log.debug("Converting Recipe Command to Domain");
        Recipe recipe = Recipe.builder().id(recipeCommand.getId()).description(recipeCommand.getDescription())
                .cookTime(recipeCommand.getCookTime()).servings(recipeCommand.getServings())
                .directions(recipeCommand.getDirections()).source(recipeCommand.getSource()).url(recipeCommand.getUrl())
                .prepTime(recipeCommand.getPrepTime()).image(recipeCommand.getImage()).build();
        if(StringUtils.isEmpty(recipeCommand.getId())) {
            recipe.setId(null);
        }
        recipe.setNotes(notesMapper.convertCommandToDomain(recipeCommand.getNotes()));
        if (recipeCommand.getDifficulty() != null) {
            recipe.setDifficulty(Difficulty.valueOf(recipeCommand.getDifficulty().name()));
        }

        val categories = recipeCommand.getCategories();
        if (CollectionUtils.isNotEmpty(categories)) {
            Set<Category> categorySet = categories.stream().map(categoryCommand -> categoryMapper.convertCommandToDomain(categoryCommand)).collect(Collectors.toCollection(() -> new HashSet<>(categories.size())));
            recipe.setCategories(categorySet);
        }

        val recipeCommandIngredients = recipeCommand.getIngredients();
        if (CollectionUtils.isNotEmpty(recipeCommandIngredients)) {
            Set<Ingredient> ingredients = recipeCommandIngredients.stream().map(ingredientCommand -> ingredientMapper.convertCommandToDomain(ingredientCommand)).collect(Collectors.toCollection(() -> new HashSet<>(recipeCommandIngredients.size())));
            recipe.addIngredients(ingredients);
        }
        log.debug("Recipe Domain ::" + recipe.toString());
        return recipe;
    }

    public RecipeCommand convertDomainToCommand(final Recipe recipe) {

        log.debug("Converting recipe domain to command");
        val recipeCommand = RecipeCommand.builder().id(recipe.getId()).directions(recipe.getDirections())
                .cookTime(recipe.getCookTime()).description(recipe.getDescription()).prepTime(recipe.getPrepTime())
                .servings(recipe.getServings()).source(recipe.getSource()).url(recipe.getUrl())
                .image(recipe.getImage()).build();

        recipeCommand.setNotes(notesMapper.convertDomainToCommand(recipe.getNotes()));

        if (recipe.getDifficulty() != null) {
            recipeCommand.setDifficulty(DifficultyCommand.valueOf(recipe.getDifficulty().name()));
        }

        if (CollectionUtils.isNotEmpty(recipe.getIngredients())) {
            Set<IngredientCommand> ingredients = recipe.getIngredients().stream().map(ingredient -> ingredientMapper.convertDomainToCommand(ingredient)).collect(Collectors.toSet());
            recipeCommand.setIngredients(new ArrayList<>(ingredients));
        }

        if (CollectionUtils.isNotEmpty(recipe.getCategories())) {
            Set<CategoryCommand> categoryCommands = recipe.getCategories().stream().map(category -> categoryMapper.convertDomainToCommand(category)).collect(Collectors.toSet());
            recipeCommand.setCategories(categoryCommands);
        }
        log.debug("RecipeCommand ::" + recipeCommand.toString());
        return recipeCommand;
    }
}
