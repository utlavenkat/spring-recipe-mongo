package venkat.org.springframework.springrecipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;


@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class Notes {

    @Id
    private String id;

    @NonNull
    private String notes;
}
