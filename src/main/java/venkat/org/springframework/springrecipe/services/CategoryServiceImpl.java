package venkat.org.springframework.springrecipe.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import venkat.org.springframework.springrecipe.command.CategoryCommand;
import venkat.org.springframework.springrecipe.domain.Category;
import venkat.org.springframework.springrecipe.mappers.CategoryMapper;
import venkat.org.springframework.springrecipe.repositories.CategoryRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Override
    public CategoryCommand getByCategoryName(String categoryName) {
        CategoryCommand categoryCommand = null;
        Optional<Category> savedCategory = categoryRepository.findByCategoryName(categoryName);
        if (savedCategory.isPresent()) {
            categoryCommand = categoryMapper.convertDomainToCommand(savedCategory.get());
        }
        return categoryCommand;
    }
}
