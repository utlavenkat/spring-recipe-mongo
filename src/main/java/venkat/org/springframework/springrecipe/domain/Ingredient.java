package venkat.org.springframework.springrecipe.domain;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {

    private Long id;

    @NonNull
    private String description;
    @NonNull
    private BigDecimal amount;


    private Recipe recipe;

    @NonNull
    private UnitOfMeasure unitOfMeasure;
}
