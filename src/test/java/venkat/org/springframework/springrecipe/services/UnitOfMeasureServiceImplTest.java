package venkat.org.springframework.springrecipe.services;

import lombok.val;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import venkat.org.springframework.springrecipe.command.UnitOfMeasureCommand;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;
import venkat.org.springframework.springrecipe.repositories.reactive.UnitOfMeasureReactiveRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class UnitOfMeasureServiceImplTest {

    private UnitOfMeasureService unitOfMeasureService;
    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository);
    }
    @After
    public void tearDown() {
        unitOfMeasureRepository = null;
        unitOfMeasureService = null;
    }

    @Test
    public void getUnitOfMeasureByUom() {
        val uom = "Cup";
        val savedUnitOfMeasure = UnitOfMeasure.builder().uom(uom).build();
        savedUnitOfMeasure.setId("1234");
        when(unitOfMeasureRepository.findByUom(uom)).thenReturn(Mono.just(savedUnitOfMeasure));
        Mono<UnitOfMeasureCommand> unitOfMeasureMono = unitOfMeasureService.getUnitOfMeasureByUom(uom);
        UnitOfMeasureCommand unitOfMeasure = unitOfMeasureMono.block();
        Assert.assertNotNull(unitOfMeasure);
        Assert.assertNotNull(unitOfMeasure.getId());
        Assert.assertEquals(uom,unitOfMeasure.getUom());
        verify(unitOfMeasureRepository,times(1)).findByUom(uom);
    }

    @Test
    public void testGetUnitOfMeasureByUom_Invalid() {
        val uom ="doenotexits";
        when(unitOfMeasureRepository.findByUom(uom)).thenReturn(Mono.empty());
        Assert.assertNull(unitOfMeasureService.getUnitOfMeasureByUom(uom).block());
    }

    @Test
    public void getAllUnitOfMeasures() {
        UnitOfMeasure  uom1 = UnitOfMeasure.builder().id("1").uom("TableSpoon").build();
        UnitOfMeasure uom2 =  UnitOfMeasure.builder().id("2").uom("Cup").build();

        when(unitOfMeasureRepository.findAll()).thenReturn(Flux.just(uom1,uom2));

        final Flux<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.getAllUnitOfMeasures();
        assertEquals(2, unitOfMeasureCommands.collectList().block().size());

    }
}