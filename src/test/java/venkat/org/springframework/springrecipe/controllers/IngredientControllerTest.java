package venkat.org.springframework.springrecipe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.command.IngredientCommand;
import venkat.org.springframework.springrecipe.command.RecipeCommand;
import venkat.org.springframework.springrecipe.command.UnitOfMeasureCommand;
import venkat.org.springframework.springrecipe.services.IngredientService;
import venkat.org.springframework.springrecipe.services.RecipeService;
import venkat.org.springframework.springrecipe.services.UnitOfMeasureService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Ignore
public class IngredientControllerTest {

    private static final String VIEW_NAME_INGREDIENT_LIST = "/recipe/ingredients/list";
    private static final String VIEW_NAME_INGREDIENT_SHOW = "/recipe/ingredients/show";
    private static final String VIEW_NAME_INGREDIENT_FORM = "/recipe/ingredients/ingredientform";

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;


    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        IngredientController controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getRecipeIngredients() throws Exception {
        //Given
        String recipeId = "1";
        RecipeCommand recipeCommand = RecipeCommand.builder().id(recipeId).build();
        when(recipeService.findRecipeById(recipeId)).thenReturn(Mono.just(recipeCommand));

        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/" + recipeId + "/ingredients"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name(VIEW_NAME_INGREDIENT_LIST));

        verify(recipeService, times(1)).findRecipeById(anyString());
    }

    @Test
    public void getIngredientById() throws Exception {
        //Given
        Long id = 1L;

        //When
        when(ingredientService.findByRecipeIdAndIngredientId(anyString(),anyString())).thenReturn(Mono.just(IngredientCommand.builder().id("1").build()));
        ResultActions resultActions = mockMvc.perform(get("/recipe/1234/ingredient/" + id + "/view"));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().size(1))
                .andExpect(view().name(VIEW_NAME_INGREDIENT_SHOW));
        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyString(),anyString());
    }

    @Test
    public void editIngredientById() throws Exception {
        //Given
        Long id = 1L;
        when(ingredientService.findByRecipeIdAndIngredientId(anyString(),anyString())).thenReturn(Mono.just(IngredientCommand.builder().id("1")
                .description("Tomato").amount(BigDecimal.ONE).build()));

        UnitOfMeasureCommand uom1 = UnitOfMeasureCommand.builder().id("1").uom("TableSpoon").build();
        UnitOfMeasureCommand uom2 = UnitOfMeasureCommand.builder().id("2").uom("Cup").build();

        when(unitOfMeasureService.getAllUnitOfMeasures()).thenReturn(Flux.just(uom1,uom2));


        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/1234/ingredient/" + id + "/edit"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME_INGREDIENT_FORM))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("ingredient", "uomList"));
    }

    @Test
    public void saveIngredient() throws Exception {

        //Given
        IngredientCommand ingredientCommand = IngredientCommand.builder().amount(BigDecimal.ONE).description("Tomato")
                .recipeId("1234").build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ingredientCommand);
        ingredientCommand.setId("100");
        when(ingredientService.save(any(IngredientCommand.class))).thenReturn(Mono.just(ingredientCommand));

        //when
        ResultActions resultActions = mockMvc.perform(post("/recipe/ingredient").param("recipeId","1234").content(jsonInString));

        //then
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(view().name("redirect:/recipe/1234/ingredients"));
        verify(ingredientService, times(1)).save(any(IngredientCommand.class));

    }


    @Test
    public void newIngredientForm() throws Exception {
        //Given
        Long recipeId = 1L;
        UnitOfMeasureCommand uom1 = UnitOfMeasureCommand.builder().id("1").uom("TableSpoon").build();
        UnitOfMeasureCommand uom2 = UnitOfMeasureCommand.builder().id("2").uom("Cup").build();

        when(unitOfMeasureService.getAllUnitOfMeasures()).thenReturn(Flux.just(uom1,uom2));

        //when
        final ResultActions resultActions = mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/new"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME_INGREDIENT_FORM))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("ingredient", "uomList"));
        verify(unitOfMeasureService, times(1)).getAllUnitOfMeasures();
    }

    @Test
    public void deleteIngredientById() throws Exception {
        //Given
        final String recipeId = "1";
        final Long ingredientId = 1L;
        when(recipeService.findRecipeById(anyString())).thenReturn(Mono.just(RecipeCommand.builder().id(recipeId).build()));

        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/"
                + ingredientId + "/delete"));

        //then
        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/"+recipeId+"/ingredients"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("recipe"));

        verify(ingredientService, times(1)).deleteById(anyString(),anyString());
        verify(recipeService, times(1)).findRecipeById(anyString());
    }
}