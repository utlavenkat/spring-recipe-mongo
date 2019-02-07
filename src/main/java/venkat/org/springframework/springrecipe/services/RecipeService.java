package venkat.org.springframework.springrecipe.services;

import venkat.org.springframework.springrecipe.command.RecipeCommand;

import java.util.Set;

public interface RecipeService {
    RecipeCommand saveRecipe(final RecipeCommand recipe);

    Set<RecipeCommand> getAllRecipes();

    RecipeCommand findRecipeById(final String id);

    void deleteRecipe(final String id);
}
