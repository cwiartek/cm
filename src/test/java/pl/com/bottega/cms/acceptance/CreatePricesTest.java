package pl.com.bottega.cms.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.cms.application.CommandGateway;
import pl.com.bottega.cms.model.Movie;
import pl.com.bottega.cms.model.Ticket;
import pl.com.bottega.cms.model.commands.CreateCinemaCommand;
import pl.com.bottega.cms.model.commands.CreateMovieCommand;
import pl.com.bottega.cms.model.commands.SetTicketPricesCommand;
import pl.com.bottega.cms.model.repositories.MovieRepository;
import pl.com.bottega.cms.model.repositories.ShowRepository;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreatePricesTest extends AcceptanceTest {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CommandGateway commandGateway;

    private void createMovie(){
        Set<String> actors = new HashSet<>();
        actors.add("Jacek");
        actors.add("Kowalski");

        Set<String> genres = new HashSet<>();
        genres.add("a");
        genres.add("b");

        CreateMovieCommand creteCreateMovieCommand = new CreateMovieCommand();
        creteCreateMovieCommand.setActors(actors);
        creteCreateMovieCommand.setDescription("description");
        creteCreateMovieCommand.setGenres(genres);
        creteCreateMovieCommand.setLength(200);
        creteCreateMovieCommand.setMinAge(16);
        creteCreateMovieCommand.setTitle("title");
        commandGateway.execute(creteCreateMovieCommand);

    }

    private void createCinema() {
        CreateCinemaCommand createCinemaCommand = new CreateCinemaCommand();
        createCinemaCommand.setCity("Lublin");
        createCinemaCommand.setName("Bajka");
        commandGateway.execute(createCinemaCommand);
    }


    @Test
    public void shouldAddPriceToMovie(){
        createMovie();
        createCinema();
        BigDecimal bigDecimal = new BigDecimal("100.00");

        Map<String, BigDecimal> mapPrices = new HashMap<>();
        mapPrices.put("regular", bigDecimal);
        mapPrices.put("student", bigDecimal);
        SetTicketPricesCommand  setTicketPricesCommand = new SetTicketPricesCommand();
        setTicketPricesCommand.setMovieId(1L);
        setTicketPricesCommand.setPrices(mapPrices);
        commandGateway.execute(setTicketPricesCommand);


        Movie movie = movieRepository.get(1L);
        List<String> kind;
        List<BigDecimal> prices;


//        assertArrayEquals(movie.getPrices().values(),);





    }


}
