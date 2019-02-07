package venkat.org.springframework.springrecipe.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import venkat.org.springframework.springrecipe.command.RecipeCommand;
import venkat.org.springframework.springrecipe.services.ImageService;
import venkat.org.springframework.springrecipe.services.RecipeService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    private static final String VIEW_NAME_RECIPE_IMAGE_FORM = "/recipe/imageUploadForm";


    @Mock
    private ImageService imageService;

    @Mock
    private RecipeService recipeService;

    private ImageController imageController;

    private MockMvc mockMvc;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(recipeService, imageService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    public void getImageForm() throws Exception {
        //Given
        RecipeCommand recipeCommand = RecipeCommand.builder().id("1").build();

        when(recipeService.findRecipeById(anyString())).thenReturn(recipeCommand);

        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/1/image"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name(VIEW_NAME_RECIPE_IMAGE_FORM));

        verify(recipeService, times(1)).findRecipeById(anyString());

    }

    @Test
    public void handleImagePost() throws Exception {
        //Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "testing.txt",
                "text/plain", "My Yummy recipes".getBytes());

        //when
        ResultActions resultActions = mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile));

        //then
        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/view"));
        verify(imageService, times(1)).saveImageFile(anyString(), any());
    }

    @Test
    public void renderRecipeImage() throws Exception {
        //Given
        String fileText = "This is test file";
        Byte[] byteArray = new Byte[fileText.length()];
        int i = 0;
        for (Byte wrapperByte : fileText.getBytes()) {
            byteArray[i++] = wrapperByte;
        }
        RecipeCommand recipeCommand = RecipeCommand.builder().id("1").image(byteArray).build();

        when(recipeService.findRecipeById(anyString())).thenReturn(recipeCommand);

        //when
        MockHttpServletResponse servletResponse = mockMvc.perform(get("/recipe/1/recipeImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertEquals("image/jpeg", servletResponse.getContentType());
        assertEquals(byteArray.length, servletResponse.getContentAsByteArray().length);
    }

}