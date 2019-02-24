package venkat.org.springframework.springrecipe.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.command.CategoryCommand;
import venkat.org.springframework.springrecipe.domain.Category;
import venkat.org.springframework.springrecipe.mappers.CategoryMapper;
import venkat.org.springframework.springrecipe.repositories.reactive.CategoryReactiveRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryReactiveRepository categoryRepository;
    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Override
    public Mono<CategoryCommand> getByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).map(category -> categoryMapper.convertDomainToCommand(category));
    }
}
