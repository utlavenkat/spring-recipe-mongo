package venkat.org.springframework.springrecipe.repositories.reactive;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.domain.Category;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTestIT {

    @Autowired
    private CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() {
        categoryReactiveRepository.deleteAll().block();

        Category category = new Category();
        category.setCategoryName("Category 1");
        categoryReactiveRepository.save(category).block();
    }
    @After
    public void tearDown() {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testCreate() {
        Category category = new Category();
        category.setCategoryName("Test Category");
        categoryReactiveRepository.save(category).block();
        assertEquals(2,categoryReactiveRepository.count().block().longValue());
    }

    @Test
    public void testFindByName() {
        Mono<Category> categoryMono = categoryReactiveRepository.findByCategoryName("Category 1");
        assertNotNull(categoryMono.block().getId());
    }
}