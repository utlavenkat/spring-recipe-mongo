package venkat.org.springframework.springrecipe.services;

import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.command.IngredientCommand;

public interface IngredientService {

    Mono<IngredientCommand> save(IngredientCommand ingredientCommand);

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<Void> deleteById(final String recipeId,final String id);
}
