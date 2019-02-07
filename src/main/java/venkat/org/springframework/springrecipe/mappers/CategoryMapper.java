package venkat.org.springframework.springrecipe.mappers;

import lombok.extern.slf4j.Slf4j;
import venkat.org.springframework.springrecipe.command.CategoryCommand;
import venkat.org.springframework.springrecipe.domain.Category;

@Slf4j
public class CategoryMapper {

    public Category convertCommandToDomain(CategoryCommand categoryCommand) {
        log.debug("Converting Category Command to Domain");
        Category category = null;
        if (categoryCommand != null) {
            category = new Category();
            category.setCategoryName(categoryCommand.getCategoryName());
            category.setId(categoryCommand.getId());
        }
        log.debug("Category Domain ::" + category);
        return category;
    }

    public CategoryCommand convertDomainToCommand(Category category) {
        log.debug("Converting Category Domain to Command");
        CategoryCommand categoryCommand = null;
        if (category != null) {
            categoryCommand = CategoryCommand.builder().id(category.getId()).categoryName(category.getCategoryName()).build();
        }
        log.debug("Category Command ::" + categoryCommand);
        return categoryCommand;
    }
}
