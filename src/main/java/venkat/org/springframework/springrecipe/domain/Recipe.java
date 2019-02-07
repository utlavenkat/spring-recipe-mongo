package venkat.org.springframework.springrecipe.domain;


import lombok.*;
import org.apache.commons.collections4.CollectionUtils;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class Recipe implements Serializable {

    private Long id;

    private String description;

    private Integer prepTime;

    private Integer cookTime;

    private Integer servings;

    private String source;

    private String url;

    private String directions;

    private Byte[] image;


    private Notes notes;

    private Set<Ingredient> ingredients = new HashSet<>();


    private Difficulty difficulty;


    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        notes.setRecipe(this);
        this.notes = notes;
    }

    public void addIngredients(Set<Ingredient> ingredients) {
        if (this.ingredients == null) {
            this.ingredients = new HashSet<>();
        }
        if (CollectionUtils.isNotEmpty(ingredients)) {
            ingredients.forEach(ingredient -> {
                ingredient.setRecipe(this);
                this.ingredients.add(ingredient);
            });
        }
    }
}
