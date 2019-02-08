package venkat.org.springframework.springrecipe.services;

import venkat.org.springframework.springrecipe.command.IngredientCommand;

public interface IngredientService {

    IngredientCommand save(IngredientCommand ingredientCommand);

    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    void deleteById(final String recipeId,final String id);
}
