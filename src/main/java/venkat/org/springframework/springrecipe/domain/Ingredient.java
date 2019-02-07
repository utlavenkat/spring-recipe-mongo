package venkat.org.springframework.springrecipe.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String description;
    @NonNull
    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    @NonNull
    @OneToOne
    private UnitOfMeasure unitOfMeasure;
}
