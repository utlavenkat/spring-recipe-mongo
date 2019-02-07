package venkat.org.springframework.springrecipe.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Category {

    private Long id;
    private String categoryName;
    private Set<Recipe> recipes = new HashSet<>();

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
}
