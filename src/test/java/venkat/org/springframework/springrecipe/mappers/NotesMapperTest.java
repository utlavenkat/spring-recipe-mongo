package venkat.org.springframework.springrecipe.mappers;

import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import venkat.org.springframework.springrecipe.command.NotesCommand;
import venkat.org.springframework.springrecipe.domain.Notes;
import venkat.org.springframework.springrecipe.domain.Recipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NotesMapperTest {

    private NotesMapper notesMapper;

    @Before
    public void setUp() {
        notesMapper = new NotesMapper();
    }

    @After
    public void tearDown() {
        notesMapper = null;
    }

    @Test
    public void convertCommandToDomain() {
        val notesCommand = NotesCommand.builder().id(1L).notes("Test Notes").build();
        val notes = notesMapper.convertCommandToDomain(notesCommand);

        assertNotNull(notes);
        assertEquals(notesCommand.getId(), notes.getId());
        assertEquals(notesCommand.getNotes(), notes.getNotes());
    }

    @Test
    public void convertDomainToCommand() {
        val notes = new Notes("Test Notes", new Recipe());
        notes.setId(1L);

        val notesCommand = notesMapper.convertDomainToCommand(notes);

        assertNotNull(notesCommand);
        assertEquals(notes.getId(), notesCommand.getId());
        assertEquals(notes.getNotes(), notesCommand.getNotes());
    }
}