package venkat.org.springframework.springrecipe.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import venkat.org.springframework.springrecipe.command.IngredientCommand;
import venkat.org.springframework.springrecipe.domain.Ingredient;
import venkat.org.springframework.springrecipe.domain.Recipe;

@Slf4j
public class IngredientMapper {

    private UnitOfMeasureMapper unitOfMeasureMapper;

    public IngredientMapper() {
        unitOfMeasureMapper = new UnitOfMeasureMapper();
    }

    public Ingredient convertCommandToDomain(final IngredientCommand ingredientCommand) {
        log.debug("Converting Ingredient Command to Domain");
        Ingredient ingredient = null;
        if (ingredientCommand != null) {
            ingredient = new Ingredient();
            if(!StringUtils.isEmpty(ingredientCommand.getId())) {
                ingredient.setId(ingredientCommand.getId());
            }
            ingredient.setDescription(ingredientCommand.getDescription());
            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setUnitOfMeasure(unitOfMeasureMapper.convertCommandToDomain(ingredientCommand.getUnitOfMeasure()));
        }
        log.debug("Ingredient Domain ::" + ingredient);
        return ingredient;
    }

    public IngredientCommand convertDomainToCommand(final Ingredient ingredient) {
        log.debug("Converting Ingredient Domain to Command");
        IngredientCommand ingredientCommand = null;
        if (ingredient != null) {
            ingredientCommand = IngredientCommand.builder().amount(ingredient.getAmount())
                    .description(ingredient.getDescription()).id(ingredient.getId())
                    .unitOfMeasure(unitOfMeasureMapper.convertDomainToCommand(ingredient.getUnitOfMeasure()))
                    .build();
        }
        log.debug("Ingredient Command ::" + ingredientCommand);
        return ingredientCommand;
    }
}
