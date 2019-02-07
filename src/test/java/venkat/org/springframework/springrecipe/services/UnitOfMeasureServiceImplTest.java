package venkat.org.springframework.springrecipe.services;

import lombok.val;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import venkat.org.springframework.springrecipe.command.UnitOfMeasureCommand;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;
import venkat.org.springframework.springrecipe.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class UnitOfMeasureServiceImplTest {

    private UnitOfMeasureService unitOfMeasureService;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

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
        when(unitOfMeasureRepository.findByUom(uom)).thenReturn(Optional.of(savedUnitOfMeasure));
        UnitOfMeasureCommand unitOfMeasure = unitOfMeasureService.getUnitOfMeasureByUom(uom);
        Assert.assertNotNull(unitOfMeasure);
        Assert.assertNotNull(unitOfMeasure.getId());
        Assert.assertEquals(uom,unitOfMeasure.getUom());
        verify(unitOfMeasureRepository,times(1)).findByUom(uom);
    }

    @Test
    public void testGetUnitOfMeasureByUom_Invalid() {
        val uom ="doenotexits";
        when(unitOfMeasureRepository.findByUom(uom)).thenReturn(Optional.empty());
        Assert.assertNull(unitOfMeasureService.getUnitOfMeasureByUom(uom));
    }

    @Test
    public void getUnitOfMeasureMap() {
        val uom1 = UnitOfMeasure.builder().uom("Cup").build();
        uom1.setId("1");
        val uom2 = UnitOfMeasure.builder().uom("Pint").build();
        uom2.setId("2");
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>(2);
        unitOfMeasures.add(uom1);
        unitOfMeasures.add(uom2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
        Map<String, UnitOfMeasureCommand> unitOfMeasureMap = unitOfMeasureService.getUnitOfMeasureMap();
        Assert.assertNotNull(unitOfMeasureMap);
        Assert.assertEquals(2, unitOfMeasureMap.size());
    }

    @Test
    public void getAllUnitOfMeasures() {
        final Set<UnitOfMeasure> unitOfMeasures = new HashSet<>(2);
        unitOfMeasures.add(UnitOfMeasure.builder().id("1").uom("TableSpoon").build());
        unitOfMeasures.add(UnitOfMeasure.builder().id("2").uom("Cup").build());

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        final Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.getAllUnitOfMeasures();
        assertEquals(unitOfMeasures.size(), unitOfMeasureCommands.size());

    }
}