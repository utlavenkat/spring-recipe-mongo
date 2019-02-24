package venkat.org.springframework.springrecipe.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    private String id = UUID.randomUUID().toString();

    @NonNull
    private String description;
    @NonNull
    private BigDecimal amount;

    @NonNull
    private UnitOfMeasure unitOfMeasure;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }
}
