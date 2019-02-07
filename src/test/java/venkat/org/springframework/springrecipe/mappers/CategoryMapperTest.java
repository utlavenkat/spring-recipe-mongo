package venkat.org.springframework.springrecipe.mappers;

import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import venkat.org.springframework.springrecipe.command.CategoryCommand;
import venkat.org.springframework.springrecipe.domain.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryMapperTest {
    private CategoryMapper categoryMapper;

    @Before
    public void setUp() {
        categoryMapper = new CategoryMapper();
    }

    @After
    public void tearDown() {
        categoryMapper = null;
    }

    @Test
    public void convertCommandToDomain() {
        val categoryCommand = CategoryCommand.builder().id("1").categoryName("Test Category").build();

        val category = categoryMapper.convertCommandToDomain(categoryCommand);

        assertNotNull(category);
        assertEquals(categoryCommand.getId(), category.getId());
        assertEquals(categoryCommand.getCategoryName(), category.getCategoryName());
    }

    @Test
    public void convertDomainToCommand() {
        val category = new Category();
        category.setId("1");
        category.setCategoryName("Test Category");

        val categoryCommand = categoryMapper.convertDomainToCommand(category);

        assertNotNull(categoryCommand);
        assertEquals(category.getId(), categoryCommand.getId());
        assertEquals(category.getCategoryName(), categoryCommand.getCategoryName());
    }
}