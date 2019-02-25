package venkat.org.springframework.springrecipe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.command.RecipeCommand;
import venkat.org.springframework.springrecipe.mappers.RecipeMapper;

import venkat.org.springframework.springrecipe.repositories.reactive.RecipeReactiveRepository;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeReactiveRepository recipeRepository;
    private final RecipeMapper recipeMapper = new RecipeMapper();


    public Mono<RecipeCommand> saveRecipe(final RecipeCommand recipeCommand) {
        return recipeRepository.save(recipeMapper.convertCommandToDomain(recipeCommand)).map(recipeMapper::convertDomainToCommand);
    }

    public Flux<RecipeCommand> getAllRecipes() {
        return recipeRepository.findAll().map(recipe -> recipeMapper.convertDomainToCommand(recipe));
    }

    public Mono<RecipeCommand> findRecipeById(final String id) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeMapper.convertDomainToCommand(recipe);

                    recipeCommand.getIngredients().forEach(rc -> {
                        rc.setRecipeId(recipeCommand.getId());
                    });

                    return recipeCommand;
                });
    }

    @Override
    public Mono<Void> deleteRecipe(String id) {
        recipeRepository.deleteById(id).block();
        return Mono.empty();
    }
}
