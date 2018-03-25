package pl.com.bottega.cms.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.cms.application.CommandGateway;
import pl.com.bottega.cms.application.MovieDto;
import pl.com.bottega.cms.infrastructure.JPAMovieFinder;
import pl.com.bottega.cms.model.Show;
import pl.com.bottega.cms.model.commands.*;
import pl.com.bottega.cms.model.repositories.MovieRepository;
import pl.com.bottega.cms.model.repositories.ShowRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateShowTest extends AcceptanceTest {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private JPAMovieFinder jpaMovieFinder;

    @Autowired
    private CommandGateway commandGateway;

    @Test
    public void shouldCreateShowWithDates() {
        createMovie();
        createCinema();

        Set<LocalDateTime> dates = new HashSet<>();
        dates.add(LocalDateTime.parse("2017-02-02T12:22"));

        CreateShowsCommand createShowsCommand = new CreateShowsCommand();
        createShowsCommand.setDates(dates);
        createShowsCommand.setMovieId(1L);
        createShowsCommand.setCinemaId(1L);
        commandGateway.execute(createShowsCommand);

        Show show = showRepository.getShow(1L);
        assertTrue(show.isTakePlaceInDate(LocalDate.parse("2017-02-02")));
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateShowWithEmptyDates() {
        createMovie();
        createCinema();

        Set<LocalDateTime> dates = new HashSet<>();

        CreateShowsCommand createShowsCommand = new CreateShowsCommand();
        createShowsCommand.setDates(dates);
        createShowsCommand.setMovieId(1L);
        createShowsCommand.setCinemaId(1L);
        commandGateway.execute(createShowsCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateShowWithEmptyCalendarWeekdays() {
        createMovie();
        createCinema();

        Set<LocalTime> hours = new HashSet<>();
        hours.add(LocalTime.parse("11:00"));
        hours.add(LocalTime.parse("18:00"));

        Set<String> weekDays = new HashSet<>();

        ShowsCalendar showsCalendar = new ShowsCalendar();
        showsCalendar.setFromDate(LocalDateTime.parse("2018-02-04T12:00"));
        showsCalendar.setUntilDate(LocalDateTime.parse("2018-02-05T12:00"));
        showsCalendar.setHours(hours);
        showsCalendar.setWeekDays(weekDays);

        CreateShowsCommand createShowsCommand = new CreateShowsCommand();
        createShowsCommand.setCalendar(showsCalendar);
        createShowsCommand.setMovieId(1L);
        createShowsCommand.setCinemaId(1L);
        commandGateway.execute(createShowsCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateShowWithEmptyCalendarHours() {
        createMovie();
        createCinema();

        Set<LocalTime> hours = new HashSet<>();

        Set<String> weekDays = new HashSet<>();
        weekDays.add("Monday");
        weekDays.add("Sunday");

        ShowsCalendar showsCalendar = new ShowsCalendar();
        showsCalendar.setFromDate(LocalDateTime.parse("2018-02-04T12:00"));
        showsCalendar.setUntilDate(LocalDateTime.parse("2018-02-05T12:00"));
        showsCalendar.setHours(hours);
        showsCalendar.setWeekDays(weekDays);

        CreateShowsCommand createShowsCommand = new CreateShowsCommand();
        createShowsCommand.setCalendar(showsCalendar);
        createShowsCommand.setMovieId(1L);
        createShowsCommand.setCinemaId(1L);
        commandGateway.execute(createShowsCommand);
    }

    @Test
    public void shouldCreateShowWithCalendar() {
        createMovie();
        createCinema();

        Set<LocalTime> hours = new HashSet<>();
        hours.add(LocalTime.parse("11:00"));
        hours.add(LocalTime.parse("18:00"));

        Set<String> weekDays = new HashSet<>();
        weekDays.add("Monday");
        weekDays.add("Sunday");

        ShowsCalendar showsCalendar = new ShowsCalendar();
        showsCalendar.setFromDate(LocalDateTime.parse("2018-02-04T12:00"));
        showsCalendar.setUntilDate(LocalDateTime.parse("2018-02-05T12:00"));
        showsCalendar.setHours(hours);
        showsCalendar.setWeekDays(weekDays);

        CreateShowsCommand createShowsCommand = new CreateShowsCommand();
        createShowsCommand.setCalendar(showsCalendar);
        createShowsCommand.setMovieId(1L);
        createShowsCommand.setCinemaId(1L);
        commandGateway.execute(createShowsCommand);

        List<MovieDto> movies = jpaMovieFinder.getMovies(1L, LocalDate.parse("2018-02-05"));
        assertEquals(movies.size(), 1);
        assertEquals(movies.get(0).getShows().get(0).getTime(), LocalTime.parse("11:00"));

        movies = jpaMovieFinder.getMovies(1L, LocalDate.parse("2018-02-04"));
        assertEquals(movies.size(), 1);
        assertEquals(movies.get(0).getShows().get(0).getTime(), LocalTime.parse("18:00"));
    }

    private void createCinema() {
        CreateCinemaCommand createCinemaCommand = new CreateCinemaCommand();
        createCinemaCommand.setCity("Lublin");
        createCinemaCommand.setName("Bajka");
        commandGateway.execute(createCinemaCommand);
    }

    private void createMovie() {
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
    }

}
