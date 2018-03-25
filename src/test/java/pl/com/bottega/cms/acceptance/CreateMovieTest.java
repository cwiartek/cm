package pl.com.bottega.cms.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.cms.application.*;
import pl.com.bottega.cms.model.Movie;
import pl.com.bottega.cms.model.commands.CommandInvalidException;
import pl.com.bottega.cms.model.commands.CreateMovieCommand;
import pl.com.bottega.cms.model.repositories.MovieRepository;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateMovieTest extends AcceptanceTest {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void shouldCreateMovie() {
        Set<String> actors = new HashSet<>();
        actors.add("John");
        actors.add("Samara");

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

        Movie movie = movieRepository.get(1L);
        assertEquals(movie.getActors(), actors);
        assertEquals(movie.getDescription(), "description");
        assertEquals(movie.getGenres(), genres);
        assertEquals(movie.getLength(), new Integer(200));
        assertEquals(movie.getMinAge(), new Integer(16));
        assertEquals(movie.getTitle(), "title");
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToAddWithEmptyActors() {
        Set<String> actors = new HashSet<>();
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

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToAddWithEmptyGenres() {
        Set<String> actors = new HashSet<>();
        actors.add("John");
        actors.add("Samara");

        Set<String> genres = new HashSet<>();
        genres.add("a");
        genres.add(" ");

        CreateMovieCommand creteCreateMovieCommand = new CreateMovieCommand();
        creteCreateMovieCommand.setActors(actors);
        creteCreateMovieCommand.setDescription("description");
        creteCreateMovieCommand.setGenres(genres);
        creteCreateMovieCommand.setLength(200);
        creteCreateMovieCommand.setMinAge(16);
        creteCreateMovieCommand.setTitle("title");
        commandGateway.execute(creteCreateMovieCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToNegativeAge() {
        Set<String> actors = new HashSet<>();
        actors.add("John");
        actors.add("Samara");

        Set<String> genres = new HashSet<>();
        genres.add("a");
        genres.add("b");

        CreateMovieCommand creteCreateMovieCommand = new CreateMovieCommand();
        creteCreateMovieCommand.setActors(actors);
        creteCreateMovieCommand.setDescription("description");
        creteCreateMovieCommand.setGenres(genres);
        creteCreateMovieCommand.setLength(200);
        creteCreateMovieCommand.setMinAge(-16);
        creteCreateMovieCommand.setTitle("title");
        commandGateway.execute(creteCreateMovieCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToNegativeLenght() {
        Set<String> actors = new HashSet<>();
        actors.add("John");
        actors.add("Samara");

        Set<String> genres = new HashSet<>();
        genres.add("a");
        genres.add("b");

        CreateMovieCommand creteCreateMovieCommand = new CreateMovieCommand();
        creteCreateMovieCommand.setActors(actors);
        creteCreateMovieCommand.setDescription("description");
        creteCreateMovieCommand.setGenres(genres);
        creteCreateMovieCommand.setLength(-200);
        creteCreateMovieCommand.setMinAge(16);
        creteCreateMovieCommand.setTitle("title");
        commandGateway.execute(creteCreateMovieCommand);
    }
}
