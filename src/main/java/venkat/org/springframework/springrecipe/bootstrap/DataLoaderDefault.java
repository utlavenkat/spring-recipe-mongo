package venkat.org.springframework.springrecipe.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import venkat.org.springframework.springrecipe.command.*;
import venkat.org.springframework.springrecipe.services.CategoryService;
import venkat.org.springframework.springrecipe.services.RecipeService;
import venkat.org.springframework.springrecipe.services.UnitOfMeasureService;

import java.math.BigDecimal;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Profile("default")
public class DataLoaderDefault implements CommandLineRunner {
    private final UnitOfMeasureService unitOfMeasureService;
    private final RecipeService recipeService;
    private final CategoryService categoryService;

    private Map<String, UnitOfMeasureCommand> unitOfMeasureCommandMap;

    @Override
    public void run(String... args) {
        unitOfMeasureCommandMap = unitOfMeasureService.getUnitOfMeasureMap();
        prepareGuacamoleRecipe();
        prepareGrillChickenRecipe();
    }

    private void prepareGuacamoleRecipe() {

        RecipeCommand guacamole = RecipeCommand.builder().description("Guacamole").cookTime(20).servings(4)
                .directions("Be careful handling chiles if using. Wash your hands throughly  after handling and do not touch your eyes.")
                .source("Simply Recipe").url("http://www.simplyrecipes.com").prepTime(10).difficulty(DifficultyCommand.EASY)
                .build();

        prepareGuacamoliIngredients(guacamole);
        guacamole.addCategory(categoryService.getByCategoryName("MEXICAN"));
        NotesCommand guacamoleNote = NotesCommand.builder().notes("Garnish with red radishes or jicama. Serve with tortilla chips").recipe(guacamole).build();
        guacamole.setNotes(guacamoleNote);

        recipeService.saveRecipe(guacamole);
    }

    private void prepareGrillChickenRecipe() {

        RecipeCommand grilledChicken = RecipeCommand.builder().description("Spicy Grilled Chicken Tacos").cookTime(15)
                .servings(6).prepTime(20).source("Simply Recipe").url("http://www.simplyrecipes.com")
                .difficulty(DifficultyCommand.EASY).directions("1.2.3.4").build();

        prepareGrillChickenIngredients(grilledChicken);
        grilledChicken.addCategory(categoryService.getByCategoryName("MEXICAN"));
        grilledChicken.setNotes(NotesCommand.builder().notes("Grilled Chicken Notes goes here").build());

        recipeService.saveRecipe(grilledChicken);
    }

    private void prepareGuacamoliIngredients(final RecipeCommand guacamoli) {

        guacamoli.addIngredient(IngredientCommand.builder().description("Ripe Avocados").amount(BigDecimal.valueOf(2)).unitOfMeasure(unitOfMeasureCommandMap.get("Ripe")).build());
        guacamoli.addIngredient(IngredientCommand.builder().description("Kosher Salt").amount(BigDecimal.valueOf(0.5)).unitOfMeasure(unitOfMeasureCommandMap.get("Teaspoon")).build());
        guacamoli.addIngredient(IngredientCommand.builder().description("Lime Juice").amount(BigDecimal.valueOf(1)).unitOfMeasure(unitOfMeasureCommandMap.get("Tablespoon")).build());
        guacamoli.addIngredient(IngredientCommand.builder().description("Minced Red Onion").amount(BigDecimal.valueOf(2)).unitOfMeasure(unitOfMeasureCommandMap.get("Tablespoon")).build());
        guacamoli.addIngredient(IngredientCommand.builder().description("Serrano Chiles").amount(BigDecimal.valueOf(2)).unitOfMeasure(unitOfMeasureCommandMap.get("Each")).build());
        guacamoli.addIngredient(IngredientCommand.builder().description("Serrano Stems").amount(BigDecimal.valueOf(2)).unitOfMeasure(unitOfMeasureCommandMap.get("Each")).build());
        guacamoli.addIngredient(IngredientCommand.builder().description("Serrano Seeds").amount(BigDecimal.valueOf(2)).unitOfMeasure(unitOfMeasureCommandMap.get("Each")).build());
        guacamoli.addIngredient(IngredientCommand.builder().description("Cilantro").amount(BigDecimal.valueOf(2)).unitOfMeasure(unitOfMeasureCommandMap.get("Each")).build());
        guacamoli.addIngredient(IngredientCommand.builder().description("Black Pepper").amount(BigDecimal.valueOf(1)).unitOfMeasure(unitOfMeasureCommandMap.get("Each")).build());
        guacamoli.addIngredient(IngredientCommand.builder().description("Tomato").amount(BigDecimal.valueOf(0.5)).unitOfMeasure(unitOfMeasureCommandMap.get("Each")).build());
    }

    private void prepareGrillChickenIngredients(final RecipeCommand grillChicken) {

        grillChicken.addIngredient(IngredientCommand.builder().description("Ancho Chilli Powder").amount(BigDecimal.valueOf(2)).unitOfMeasure(unitOfMeasureCommandMap.get("Tablespoon")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Dried Oregano").amount(BigDecimal.valueOf(1)).unitOfMeasure(unitOfMeasureCommandMap.get("Teaspoon")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Dried Cumin").amount(BigDecimal.valueOf(1)).unitOfMeasure(unitOfMeasureCommandMap.get("Teaspoon")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Sugar").amount(BigDecimal.valueOf(1)).unitOfMeasure(unitOfMeasureCommandMap.get("Teaspoon")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Salt").amount(BigDecimal.valueOf(0.5)).unitOfMeasure(unitOfMeasureCommandMap.get("Teaspoon")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Clove Garlic").amount(BigDecimal.valueOf(1)).unitOfMeasure(unitOfMeasureCommandMap.get("Each")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Orange Zest").amount(BigDecimal.valueOf(1)).unitOfMeasure(unitOfMeasureCommandMap.get("Tablespoon")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Orange Juice").amount(BigDecimal.valueOf(3)).unitOfMeasure(unitOfMeasureCommandMap.get("Tablespoon")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Olive Oil").amount(BigDecimal.valueOf(2)).unitOfMeasure(unitOfMeasureCommandMap.get("Tablespoon")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Chicken Thighs").amount(BigDecimal.valueOf(6)).unitOfMeasure(unitOfMeasureCommandMap.get("Each")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Corn Tortillas").amount(BigDecimal.valueOf(3)).unitOfMeasure(unitOfMeasureCommandMap.get("Cup")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Avocados").amount(BigDecimal.valueOf(2)).unitOfMeasure(unitOfMeasureCommandMap.get("Ripe")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Cherry Tomato").amount(BigDecimal.valueOf(0.5)).unitOfMeasure(unitOfMeasureCommandMap.get("Pint")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Red Onion").amount(BigDecimal.valueOf(0.25)).unitOfMeasure(unitOfMeasureCommandMap.get("Each")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Sour Cream").amount(BigDecimal.valueOf(0.5)).unitOfMeasure(unitOfMeasureCommandMap.get("Cup")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Milk").amount(BigDecimal.valueOf(0.25)).unitOfMeasure(unitOfMeasureCommandMap.get("Cup")).build());
        grillChicken.addIngredient(IngredientCommand.builder().description("Lime").amount(BigDecimal.valueOf(1)).unitOfMeasure(unitOfMeasureCommandMap.get("Each")).build());
    }
}
