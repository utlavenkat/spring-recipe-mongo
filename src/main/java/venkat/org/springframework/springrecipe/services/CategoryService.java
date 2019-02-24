package venkat.org.springframework.springrecipe.services;

import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.command.CategoryCommand;

public interface CategoryService {
    Mono<CategoryCommand> getByCategoryName(String categoryName);
}
