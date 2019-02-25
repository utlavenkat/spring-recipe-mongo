package venkat.org.springframework.springrecipe.command;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RecipeCommand {
    private String id;

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
    private List<IngredientCommand> ingredients = new ArrayList<>();
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
            ingredients = new ArrayList<>();
        }
        return ingredients.add(ingredientCommand);
    }
}
