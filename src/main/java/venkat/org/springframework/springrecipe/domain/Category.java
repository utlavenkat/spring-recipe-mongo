package venkat.org.springframework.springrecipe.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Category {

    @Id
    private String id;
    private String categoryName;
    @DBRef
    private Set<Recipe> recipes = new HashSet<>();

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
}
