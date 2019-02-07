package venkat.org.springframework.springrecipe.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import venkat.org.springframework.springrecipe.command.RecipeCommand;
import venkat.org.springframework.springrecipe.domain.Recipe;
import venkat.org.springframework.springrecipe.exceptions.NotFoundException;
import venkat.org.springframework.springrecipe.mappers.RecipeMapper;
import venkat.org.springframework.springrecipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper = new RecipeMapper();


    public RecipeCommand saveRecipe(final RecipeCommand recipeCommand) {
        val savedRecipe = recipeRepository.save(recipeMapper.convertCommandToDomain(recipeCommand));
        return recipeMapper.convertDomainToCommand(savedRecipe);
    }

    public Set<RecipeCommand> getAllRecipes() {
        Set<RecipeCommand> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipe -> recipes.add(recipeMapper.convertDomainToCommand(recipe)));
        return recipes;
    }

    public RecipeCommand findRecipeById(final Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (!recipe.isPresent()) {
            throw new NotFoundException("Recipe not found for the ID value: " + id);
        }
        return recipeMapper.convertDomainToCommand(recipe.get());
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}
