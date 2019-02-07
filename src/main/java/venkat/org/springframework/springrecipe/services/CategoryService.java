package venkat.org.springframework.springrecipe.services;

import venkat.org.springframework.springrecipe.command.CategoryCommand;

public interface CategoryService {
    CategoryCommand getByCategoryName(String categoryName);
}
