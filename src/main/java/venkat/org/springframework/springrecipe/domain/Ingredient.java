package venkat.org.springframework.springrecipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {

    @Id
    private String id;

    @NonNull
    private String description;
    @NonNull
    private BigDecimal amount;

    @NonNull
    @DBRef
    private UnitOfMeasure unitOfMeasure;
}
