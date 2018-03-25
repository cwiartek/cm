package pl.com.bottega.cms.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.cms.application.CinemaDto;
import pl.com.bottega.cms.application.CinemaFinder;
import pl.com.bottega.cms.application.CommandGateway;
import pl.com.bottega.cms.application.CreateCinemaHandler;
import pl.com.bottega.cms.model.commands.CommandInvalidException;
import pl.com.bottega.cms.model.commands.CreateCinemaCommand;
import pl.com.bottega.cms.model.commands.ValidationErrors;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateCinemaTest extends AcceptanceTest {

    @Autowired
    private CinemaFinder cinemaFinder;

    @Autowired
    private CommandGateway commandGateway;

    @Test
    public void shouldCreateCinemas() {
        CreateCinemaCommand createCinemaCommand = new CreateCinemaCommand();
        createCinemaCommand.setCity("Lublin");
        createCinemaCommand.setName("Bajka");
        commandGateway.execute(createCinemaCommand);

        List<CinemaDto> cinemas = cinemaFinder.getAll();
        assertEquals(cinemas.get(0).getCity(), "Lublin");
        assertEquals(cinemas.get(0).getName(), "Bajka");
        assertEquals(cinemas.get(0).getId(), new Long(1));
        assertEquals(cinemas.size(), 1);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToAddTheSameCinema() {
        CreateCinemaCommand createCinemaCommand = new CreateCinemaCommand();
        createCinemaCommand.setCity("Lublin");
        createCinemaCommand.setName("Bajka");
        commandGateway.execute(createCinemaCommand);

        createCinemaCommand = new CreateCinemaCommand();
        createCinemaCommand.setCity("Lublin");
        createCinemaCommand.setName("Bajka");
        commandGateway.execute(createCinemaCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToAddEmptyCity() {
        CreateCinemaCommand createCinemaCommand = new CreateCinemaCommand();
        createCinemaCommand.setCity(" ");
        createCinemaCommand.setName("Bajka");
        commandGateway.execute(createCinemaCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToAddEmptyName() {
        CreateCinemaCommand createCinemaCommand = new CreateCinemaCommand();
        createCinemaCommand.setCity("City");
        createCinemaCommand.setName(" ");
        commandGateway.execute(createCinemaCommand);
    }
}
