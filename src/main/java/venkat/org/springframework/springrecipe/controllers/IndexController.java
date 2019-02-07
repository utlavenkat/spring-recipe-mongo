package venkat.org.springframework.springrecipe.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import venkat.org.springframework.springrecipe.services.RecipeService;

@Controller
@AllArgsConstructor
public class IndexController {
    private final RecipeService recipeService;

    @RequestMapping({"","/","/index","/index.html"})
    public String getIndexPage(final Model model) {
        model.addAttribute("recipes",recipeService.getAllRecipes());
        return "index";
    }
}
