package venkat.org.springframework.springrecipe.domain;


import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class Recipe implements Serializable {

    @Id
    private String id;

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
        this.notes = notes;
    }

    public void addIngredients(Set<Ingredient> ingredients) {
        if (this.ingredients == null) {
            this.ingredients = new HashSet<>();
        }
        if (CollectionUtils.isNotEmpty(ingredients)) {
            ingredients.forEach(ingredient -> {
                this.ingredients.add(ingredient);
            });
        }
    }

    public void addIngredient(Ingredient ingredient) {
        if (this.ingredients == null) {
            this.ingredients = new HashSet<>();
        }
         ingredients.add(ingredient);
    }
}
