package venkat.org.springframework.springrecipe.domain;

import lombok.*;


@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class Notes {

    private Long id;

    @NonNull
    private String notes;

    @NonNull
    private Recipe recipe;

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
