package venkat.org.springframework.springrecipe.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecipeCommand {
    private Long id;

    @NotBlank
    private String description;

    @Min(1)
    @Max(120)
    private Integer prepTime;
    @Min(1)
    @Max(120)
    private Integer cookTime;

    @Min(1)
    private Integer servings;
    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;
    private NotesCommand notes;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private DifficultyCommand difficulty;
    private Set<CategoryCommand> categories = new HashSet<>();
    private Byte[] image;

    public boolean addCategory(final CategoryCommand categoryCommand) {
        if (categories == null) {
            categories = new HashSet<>();
        }
        return categories.add(categoryCommand);
    }

    public boolean addIngredient(final IngredientCommand ingredientCommand) {
        if (ingredients == null) {
            ingredients = new HashSet<>();
        }
        return ingredients.add(ingredientCommand);
    }
}
