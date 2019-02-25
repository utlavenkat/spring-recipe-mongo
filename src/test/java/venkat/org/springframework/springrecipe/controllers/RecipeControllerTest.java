package venkat.org.springframework.springrecipe.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.command.DifficultyCommand;
import venkat.org.springframework.springrecipe.command.RecipeCommand;
import venkat.org.springframework.springrecipe.exceptions.NotFoundException;
import venkat.org.springframework.springrecipe.services.RecipeService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Ignore
@Slf4j
public class RecipeControllerTest {

    private static final String VIEW_NAME_RECIPE_FORM = "recipe/recipeform";
    private static final String VIEW_NAME_RECIPE_SHOW = "recipe/show";
    private static final String VIEW_NAME_INDEX = "index";
    private static final String VIEW_NAME_ERROR_PAGE_404 = "errorpages/404";
    private static final String VIEW_NAME_ERROR_PAGE_400 = "errorpages/400";




    private MockMvc mockMvc;
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    public void viewRecipe() throws Exception {
        //Given
        RecipeCommand recipe = RecipeCommand.builder().id("1").description("Test Recipe").build();
        when(recipeService.findRecipeById(anyString())).thenReturn(Mono.just(recipe));

        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/{id}/view", 1));

        //then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name(VIEW_NAME_RECIPE_SHOW));
        resultActions.andExpect(model().size(1));
        resultActions.andExpect(model().attributeExists("recipe"));
        resultActions.andExpect(model().attribute("recipe", recipe));
        verify(recipeService, times(1)).findRecipeById("1");
    }

    @Test
    @Ignore
    //This is invalid test now
    public void viewRecipe_NumberFormatException() throws Exception {
        //Given
        RecipeCommand recipe = RecipeCommand.builder().id("1").description("Test Recipe").build();
        when(recipeService.findRecipeById(anyString())).thenReturn(Mono.just(recipe));

        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/{id}/view", "abc"));

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(view().name(VIEW_NAME_ERROR_PAGE_400));
        resultActions.andExpect(model().size(1));
        resultActions.andExpect(model().attributeExists("exception"));
        verifyZeroInteractions(recipeService);
    }

    @Test
    public void viewRecipeNotFound() throws Exception {
        //Given
        when(recipeService.findRecipeById(anyString())).thenThrow(NotFoundException.class);

        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/{id}/view", 1));

        //then
        resultActions.andExpect(status().isNotFound())
                .andExpect(view().name(VIEW_NAME_ERROR_PAGE_404));

        verify(recipeService, times(1)).findRecipeById("1");
    }

    @Test
    public void editRecipeForm() throws Exception {
        //Given
        RecipeCommand recipe = RecipeCommand.builder().id("1").description("Test Recipe").build();
        when(recipeService.findRecipeById(anyString())).thenReturn(Mono.just(recipe));

        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/{id}/edit", 1));

        //then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name(VIEW_NAME_RECIPE_FORM));
        resultActions.andExpect(model().size(1));
        resultActions.andExpect(model().attributeExists("recipe"));
        resultActions.andExpect(model().attribute("recipe", recipe));
        verify(recipeService, times(1)).findRecipeById("1");
    }

    @Test
    public void getNewRecipeForm() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/new"));

        //then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name(VIEW_NAME_RECIPE_FORM));
        resultActions.andExpect(model().size(1));
        resultActions.andExpect(model().attributeExists("recipe"));
        resultActions.andExpect(model().attribute("recipe", Matchers.notNullValue()));
        verify(recipeService, times(0)).findRecipeById(anyString());
    }

    @Test
    public void postRecipeForm() throws Exception {

        //Given
        RecipeCommand inputRecipeCommand = RecipeCommand.builder().difficulty(DifficultyCommand.HARD).prepTime(0)
                .url("https://testRecipes.com").source("My source").directions("1.2.3.4").servings(4).cookTime(20)
                .description("Test Recipe").build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(inputRecipeCommand);
        System.out.println(jsonInString);
        inputRecipeCommand.setId("100");
        when(recipeService.saveRecipe(any(RecipeCommand.class))).thenReturn(Mono.just(inputRecipeCommand));

        //when
        ResultActions resultActions = mockMvc.perform(post("/recipe")
                .param("description", "some recipe")
                .param("directions", inputRecipeCommand.getDirections())
        );

        //then
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(view().name("redirect:/recipe/" + inputRecipeCommand.getId().toString() + "/view"));
        verify(recipeService, times(1)).saveRecipe(any(RecipeCommand.class));
    }

    @Test
    public void postRecipeForm_validationFail() throws Exception {

        //Given
        RecipeCommand inputRecipeCommand = RecipeCommand.builder().difficulty(DifficultyCommand.HARD).prepTime(0)
                .url("https://testRecipes.com").source("My source").directions("1.2.3.4").servings(4).cookTime(20)
                .description("Test Recipe").build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(inputRecipeCommand);
        System.out.println(jsonInString);
        inputRecipeCommand.setId("100");
        when(recipeService.saveRecipe(any(RecipeCommand.class))).thenReturn(Mono.just(inputRecipeCommand));

        //when
        ResultActions resultActions = mockMvc.perform(post("/recipe")
                .param("description", "some recipe"));

        //then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name(VIEW_NAME_RECIPE_FORM));
        verifyZeroInteractions(recipeService);
    }

    @Test
    public void deleteRecipe() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/{id}/delete", 1));

        //then
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(view().name("redirect:/" + VIEW_NAME_INDEX));
        verify(recipeService, times(1)).deleteRecipe("1");
    }
}