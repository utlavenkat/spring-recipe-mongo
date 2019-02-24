package venkat.org.springframework.springrecipe.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.command.RecipeCommand;

public interface RecipeService {
    Mono<RecipeCommand> saveRecipe(final RecipeCommand recipe);

    Flux<RecipeCommand> getAllRecipes();

    Mono<RecipeCommand> findRecipeById(final String id);

    Mono<Void> deleteRecipe(final String id);
}
