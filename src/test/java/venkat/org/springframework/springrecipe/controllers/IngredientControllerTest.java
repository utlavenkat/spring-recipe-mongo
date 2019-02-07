package venkat.org.springframework.springrecipe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
        when(recipeService.findRecipeById(recipeId)).thenReturn(recipeCommand);

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
        when(ingredientService.findIngredientById(anyString())).thenReturn(IngredientCommand.builder().id("1").build());
        ResultActions resultActions = mockMvc.perform(get("/recipe/ingredient/" + id + "/view"));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().size(1))
                .andExpect(view().name(VIEW_NAME_INGREDIENT_SHOW));
        verify(ingredientService, times(1)).findIngredientById(anyString());
    }

    @Test
    public void editIngredientById() throws Exception {
        //Given
        Long id = 1L;
        when(ingredientService.findIngredientById(anyString())).thenReturn(IngredientCommand.builder().id("1")
                .description("Tomato").amount(BigDecimal.ONE).build());

        final Set<UnitOfMeasureCommand> unitOfMeasures = new HashSet<>(2);
        unitOfMeasures.add(UnitOfMeasureCommand.builder().id("1").uom("TableSpoon").build());
        unitOfMeasures.add(UnitOfMeasureCommand.builder().id("2").uom("Cup").build());

        when(unitOfMeasureService.getAllUnitOfMeasures()).thenReturn(unitOfMeasures);


        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/ingredient/" + id + "/edit"));

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
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ingredientCommand);
        ingredientCommand.setId("100");
        when(ingredientService.save(any(IngredientCommand.class))).thenReturn(ingredientCommand);

        //when
        ResultActions resultActions = mockMvc.perform(post("/recipe/ingredient").content(jsonInString));

        //then
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(view().name("redirect:/recipe/ingredient/" + ingredientCommand
                .getId().toString() + "/view"));
        verify(ingredientService, times(1)).save(any(IngredientCommand.class));

    }


    @Test
    public void newIngredientForm() throws Exception {
        //Given
        Long recipeId = 1L;
        final Set<UnitOfMeasureCommand> unitOfMeasures = new HashSet<>(2);
        unitOfMeasures.add(UnitOfMeasureCommand.builder().id("1").uom("TableSpoon").build());
        unitOfMeasures.add(UnitOfMeasureCommand.builder().id("2").uom("Cup").build());

        when(unitOfMeasureService.getAllUnitOfMeasures()).thenReturn(unitOfMeasures);

        //when
        final ResultActions resultActions = mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/new"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME_INGREDIENT_FORM))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("ingredient", "uomList"));
        verify(unitOfMeasureService, times(1)).getAllUnitOfMeasures();
        verify(ingredientService, times(0)).findIngredientById(anyString());

    }

    @Test
    public void deleteIngredientById() throws Exception {
        //Given
        final String recipeId = "1";
        final Long ingredientId = 1L;
        when(recipeService.findRecipeById(anyString())).thenReturn(RecipeCommand.builder().id(recipeId).build());

        //when
        ResultActions resultActions = mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/"
                + ingredientId + "/delete"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME_INGREDIENT_LIST))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("recipe"));

        verify(ingredientService, times(1)).delete(anyString());
        verify(recipeService, times(1)).findRecipeById(anyString());
    }
}