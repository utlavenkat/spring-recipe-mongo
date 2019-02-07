package venkat.org.springframework.springrecipe.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitOfMeasure {
    private Long id;

    private String uom;
}
