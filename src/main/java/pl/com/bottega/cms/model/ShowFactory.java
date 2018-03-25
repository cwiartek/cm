package pl.com.bottega.cms.model;

import org.springframework.stereotype.Component;
import pl.com.bottega.cms.model.commands.CreateShowsCommand;
import pl.com.bottega.cms.model.repositories.CinemaRepository;
import pl.com.bottega.cms.model.repositories.MovieRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ShowFactory {

    private MovieRepository movieRepository;
    private CinemaRepository cinemaRepository;

    public ShowFactory(MovieRepository movieRepository, CinemaRepository cinemaRepository) {
        this.movieRepository = movieRepository;
        this.cinemaRepository = cinemaRepository;
    }

    public Collection<Show> createShows(CreateShowsCommand cmd) {
        Movie movie = movieRepository.get(cmd.getMovieId());
        Cinema cinema = cinemaRepository.get(cmd.getCinemaId());
        if (cmd.getCalendar() == null) {
            return createShowsWithDates(cmd, cinema, movie);
        }
        return createShowsWithCalendar(cmd, cinema, movie);
    }

    private Collection<Show> createShowsWithCalendar(CreateShowsCommand cmd, Cinema cinema, Movie movie) {
        Collection<Show> shows = new ArrayList<>();
        LocalDate iteratorDate = cmd.getFromDate();
        while (!iteratorDate.isAfter(cmd.getUntilDate())) {
            if (cmd.calendarContainsDate(iteratorDate)) {
                for (LocalTime time : cmd.getCalendarHours()) {
                    LocalDateTime showDateTime = LocalDateTime.of(iteratorDate, time);
                    if (cmd.calendarContainsDateTime(showDateTime)) {
                        shows.add(new Show(cinema, movie, showDateTime));
                    }
                }
            }
            iteratorDate = iteratorDate.plusDays(1);
        }
        return shows;
    }

    private Collection<Show> createShowsWithDates(CreateShowsCommand cmd, Cinema cinema, Movie movie) {
        return cmd.getDates().stream().map((d) -> new Show(cinema, movie, d)).collect(Collectors.toList());
    }

}
