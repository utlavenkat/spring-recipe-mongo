package venkat.org.springframework.springrecipe.services;

import lombok.val;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import venkat.org.springframework.springrecipe.command.CategoryCommand;
import venkat.org.springframework.springrecipe.domain.Category;
import venkat.org.springframework.springrecipe.repositories.CategoryRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class CategoryServiceImplTest {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @After
    public void tearDown() {
        categoryRepository = null;
        categoryService = null;
    }

    @Test
    public void testGetByCategoryName_Valid() {
        val categoryName = "AMERICAN";
        val mockedCategory = new Category();
        mockedCategory.setId(1234L);
        mockedCategory.setCategoryName(categoryName);

        Assert.assertNotNull(categoryService);
        when(categoryRepository.findByCategoryName(categoryName)).thenReturn(Optional.of(mockedCategory));
        CategoryCommand category = categoryService.getByCategoryName(categoryName);
        Assert.assertNotNull(category);
        Assert.assertNotNull(category.getId());
        assertEquals(mockedCategory.getId(),category.getId());
        Assert.assertEquals(mockedCategory.getCategoryName(),category.getCategoryName());

    }

    @Test
    public void testGetByCategoryName_Invalid() {
        val categoryName = "doesnotexists";
        when(categoryRepository.findByCategoryName(categoryName)).thenReturn(Optional.empty());
        Assert.assertNull(categoryService.getByCategoryName(categoryName));
    }
}