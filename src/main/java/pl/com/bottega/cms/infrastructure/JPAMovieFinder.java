package pl.com.bottega.cms.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.cms.application.MovieDto;
import pl.com.bottega.cms.application.MovieFinder;
import pl.com.bottega.cms.model.Movie;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JPAMovieFinder implements MovieFinder {

    private EntityManager entityManager;

    public JPAMovieFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<MovieDto> getMovies(long cinemaId, LocalDate date) {
        List<Movie> results = entityManager.createQuery("SELECT DISTINCT m FROM Movie m JOIN Show s ON s.movie.id = m.id WHERE s.cinema.id = :cinema_id AND s.date BETWEEN :min_date AND :max_date")
            .setParameter("cinema_id", cinemaId)
            .setParameter("min_date", LocalDateTime.of(date, LocalTime.MIN))
            .setParameter("max_date", LocalDateTime.of(date, LocalTime.MAX))
            .getResultList();

        return results.stream().map(movie -> movie.removeShowsWithoutDate(date)).map(movie -> new MovieDto(movie)).collect(Collectors.toList());
    }
}
