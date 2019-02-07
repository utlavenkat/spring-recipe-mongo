package venkat.org.springframework.springrecipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import venkat.org.springframework.springrecipe.domain.Recipe;
import venkat.org.springframework.springrecipe.repositories.RecipeRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    private ImageService imageService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFile() throws Exception {
        //Given
        MultipartFile mockMultipartFile = new MockMultipartFile("file", "testing.txt",
                "text/plain", "My Yummy recipes".getBytes());
        Recipe recipe = Recipe.builder().id("1").build();
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        //when
        imageService.saveImageFile(recipe.getId(), mockMultipartFile);

        //then
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(mockMultipartFile.getBytes().length, savedRecipe.getImage().length);

    }
}