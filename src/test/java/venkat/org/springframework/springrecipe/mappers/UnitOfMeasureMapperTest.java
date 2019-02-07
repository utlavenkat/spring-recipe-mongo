package venkat.org.springframework.springrecipe.mappers;

import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import venkat.org.springframework.springrecipe.command.UnitOfMeasureCommand;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UnitOfMeasureMapperTest {

    private UnitOfMeasureMapper unitOfMeasureMapper;

    @Before
    public void setUp() {
        unitOfMeasureMapper = new UnitOfMeasureMapper();
    }

    @After
    public void tearDown() {
        unitOfMeasureMapper = null;
    }

    @Test
    public void convertCommandToDomain() {
        UnitOfMeasureCommand unitOfMeasureCommand = UnitOfMeasureCommand.builder().id(1L).uom("Cup").build();

        val unitOfMeasure = unitOfMeasureMapper.convertCommandToDomain(unitOfMeasureCommand);
        assertNotNull(unitOfMeasure);
        assertEquals(unitOfMeasureCommand.getId(), unitOfMeasure.getId());
        assertEquals(unitOfMeasureCommand.getUom(), unitOfMeasure.getUom());

    }

    @Test
    public void convertDomainToCommand() {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().id(1L).uom("Cup").build();

        val unitOfMeasureCommand = unitOfMeasureMapper.convertDomainToCommand(unitOfMeasure);
        assertNotNull(unitOfMeasureCommand);
        assertEquals(unitOfMeasureCommand.getId(), unitOfMeasure.getId());
        assertEquals(unitOfMeasureCommand.getUom(), unitOfMeasure.getUom());
    }
}