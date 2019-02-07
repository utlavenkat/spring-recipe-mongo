package venkat.org.springframework.springrecipe.services;

import venkat.org.springframework.springrecipe.command.IngredientCommand;

public interface IngredientService {
    IngredientCommand findIngredientById(Long id);

    IngredientCommand save(IngredientCommand ingredientCommand);

    void delete(final Long id);
}
