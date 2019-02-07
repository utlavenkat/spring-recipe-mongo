package venkat.org.springframework.springrecipe.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NotesCommand {
    private String id;
    private String notes;
    private RecipeCommand recipe;
}
