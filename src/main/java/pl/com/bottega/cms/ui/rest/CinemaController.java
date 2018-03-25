package pl.com.bottega.cms.ui.rest;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.cms.application.*;
import pl.com.bottega.cms.model.commands.CreateCinemaCommand;
import pl.com.bottega.cms.model.commands.CreateShowsCommand;

import java.time.LocalDate;
import java.util.List;


@RestController
public class CinemaController {

    private CommandGateway commandGateway;
    private CinemaFinder cinemaFinder;
    private MovieFinder movieFinder;

    public CinemaController(CommandGateway commandGateway, CinemaFinder cinemaFinder, MovieFinder movieFinder) {
        this.commandGateway = commandGateway;
        this.cinemaFinder = cinemaFinder;
        this.movieFinder = movieFinder;
    }

    @PutMapping("/cinemas")
    public void create(@RequestBody CreateCinemaCommand cmd) {
        commandGateway.execute(cmd);
    }

    @GetMapping("/cinemas")
    public List<CinemaDto> getAll() {
        List<CinemaDto> resultList = cinemaFinder.getAll();
        return resultList;
    }

    @PutMapping("/cinemas/{cinemaId}/shows")
    public void createShows(@PathVariable Long cinemaId, @RequestBody CreateShowsCommand cmd) {
        cmd.setCinemaId(cinemaId);
        commandGateway.execute(cmd);
    }

    @GetMapping("/cinemas/{cinemaId}/movies")
    public List<MovieDto> findMovies(@PathVariable Long cinemaId, @RequestParam LocalDate date) {
        return movieFinder.getMovies(cinemaId, date);
    }
}
