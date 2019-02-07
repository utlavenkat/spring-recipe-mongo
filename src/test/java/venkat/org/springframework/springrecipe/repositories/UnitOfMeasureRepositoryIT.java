package venkat.org.springframework.springrecipe.repositories;


import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import venkat.org.springframework.springrecipe.domain.UnitOfMeasure;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class UnitOfMeasureRepositoryIT {
    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    public void testFindByDescription() {
         Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUom("Cup");
         assertTrue(unitOfMeasure.isPresent());
         assertEquals("Cup",unitOfMeasure.get().getUom());
    }

    @Test
    public void testSave() {
        val unitOfMeasure = UnitOfMeasure.builder().uom("TestUOM").build();
        val savedUnitOfMeasure = unitOfMeasureRepository.save(unitOfMeasure);
        assertNotNull(savedUnitOfMeasure);
        assertNotNull(savedUnitOfMeasure.getId());
        assertTrue(savedUnitOfMeasure.getId() > 0);
        assertEquals("TestUOM",savedUnitOfMeasure.getUom());
    }

    @Test
    public void testSaveAll() {
        val uom1 = UnitOfMeasure.builder().uom("TestUOM1").build();
        val uom2 = UnitOfMeasure.builder().uom("TestUOM2").build();
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>(2);
        unitOfMeasures.add(uom1);
        unitOfMeasures.add(uom2);

        val savedUnitOfMeasures = unitOfMeasureRepository.saveAll(unitOfMeasures);
        savedUnitOfMeasures.forEach(unitOfMeasure -> {
            assertNotNull(unitOfMeasure.getId());
            assertTrue(unitOfMeasure.getId() > 0);
        });
    }

    @Test
    public void testFindById_Valid() {
        long idValue = 1L;
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findById(idValue);
        assertTrue(unitOfMeasure.isPresent());
        assertEquals(idValue,unitOfMeasure.get().getId().longValue());
    }

    @Test
    public void testFindById_Invalid() {
        long idValue = 12345L;
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findById(idValue);
        assertFalse(unitOfMeasure.isPresent());
    }

    @Test
    public void testExistsById_valid() {
        long idValue = 1L;
        assertTrue(unitOfMeasureRepository.existsById(idValue));

    }
    @Test
    public void testExistsById_Invalid() {
        long idValue = 12345L;
        assertFalse(unitOfMeasureRepository.existsById(idValue));
    }

    @Test
    public void testFindAll() {
       Iterable<UnitOfMeasure> unitOfMeasures = unitOfMeasureRepository.findAll();
       assertNotNull(unitOfMeasures);
       Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
       unitOfMeasures.forEach(unitOfMeasureSet::add);
        assertEquals(9, unitOfMeasureSet.size());
    }

    @Test
    public void findAllById() {
        Set<Long> idSet = new HashSet<>(2);
        idSet.add(1L);
        idSet.add(2L);

        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>(2);
        unitOfMeasureRepository.findAllById(idSet).forEach(unitOfMeasures::add);
        assertFalse(unitOfMeasures.isEmpty());
        assertEquals(unitOfMeasures.size(), idSet.size());
        unitOfMeasures.forEach(unitOfMeasure -> assertTrue(idSet.contains(unitOfMeasure.getId())));
    }

    @Test
    public void testCount() {
        assertEquals(9,unitOfMeasureRepository.count());
    }
}