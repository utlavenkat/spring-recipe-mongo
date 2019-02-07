package venkat.org.springframework.springrecipe.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class UnitOfMeasureCommand {
    private String id;
    private String uom;
}
