package venkat.org.springframework.springrecipe.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import venkat.org.springframework.springrecipe.command.IngredientCommand;
import venkat.org.springframework.springrecipe.command.UnitOfMeasureCommand;
import venkat.org.springframework.springrecipe.services.IngredientService;
import venkat.org.springframework.springrecipe.services.RecipeService;
import venkat.org.springframework.springrecipe.services.UnitOfMeasureService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IngredientController {

    private static final String VIEW_NAME_INGREDIENT_LIST = "/recipe/ingredients/list";
    private static final String VIEW_NAME_INGREDIENT_SHOW = "/recipe/ingredients/show";
    private static final String VIEW_NAME_INGREDIENT_FORM = "/recipe/ingredients/ingredientform";

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final UnitOfMeasureService unitOfMeasureService;

    @RequestMapping(path = "/recipe/{recipeId}/ingredients")
    public String getRecipeIngredients(@PathVariable final String recipeId, final Model model) {
        log.info("Get Recipe Ingredients. Recipe Id::" + recipeId);
        model.addAttribute("recipe", recipeService.findRecipeById(recipeId));
        return VIEW_NAME_INGREDIENT_LIST;
    }

    @RequestMapping(path = "/recipe/{recipeId}/ingredient/{id}/view")
    public String getIngredientById(@PathVariable String recipeId, @PathVariable final String id, final Model model) {
        log.info("getIngredientById, Input Id::" + id);
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId,id));
        return VIEW_NAME_INGREDIENT_SHOW;
    }

    @RequestMapping(path = "/recipe/{recipeId}/ingredient/{id}/edit")
    public String editIngredientById(@PathVariable String recipeId, @PathVariable final String id, final Model model) {
        log.info("editIngredientById() Input Id::" + id);
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId,id);
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.getAllUnitOfMeasures().collectList().block());
        log.info(" Ingredient Object::" + ingredientCommand);
        return VIEW_NAME_INGREDIENT_FORM;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/recipe/ingredient")
    public String saveIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
        log.info("saveIngredient() Input Ingredient ::" + ingredientCommand);
        val savedIngredient = ingredientService.save(ingredientCommand);
        log.info("Saved Ingredient ::" + savedIngredient);
        return "redirect:/recipe/"+ingredientCommand.getRecipeId()+"/ingredients";
    }

    @RequestMapping(path = "/recipe/{id}/ingredient/new")
    public String newIngredientForm(@PathVariable final String id, final Model model) {
        final IngredientCommand ingredientCommand = IngredientCommand.builder().recipeId(id)
                .unitOfMeasure(UnitOfMeasureCommand.builder().build()).build();
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.getAllUnitOfMeasures().collectList().block());
        return VIEW_NAME_INGREDIENT_FORM;
    }

    @RequestMapping(path = "/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredientById(@PathVariable final String recipeId, @PathVariable final String id, final Model model) {
        log.info("deleteIngredientById() Input Ingredient ID::" + id);
        ingredientService.deleteById(recipeId,id);
        model.addAttribute("recipe", recipeService.findRecipeById(recipeId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
