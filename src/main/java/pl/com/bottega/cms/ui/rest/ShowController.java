package pl.com.bottega.cms.ui.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.cms.application.CinemaHallDto;
import pl.com.bottega.cms.infrastructure.JPACinemaFinder;

@RestController
public class ShowController {

    private JPACinemaFinder jpaCinemaFinder;

    public ShowController(JPACinemaFinder jpaCinemaFinder) {
        this.jpaCinemaFinder = jpaCinemaFinder;
    }

    @GetMapping("/shows/{showId}/seats")
    public CinemaHallDto getSeats(@PathVariable Long showId){
        return jpaCinemaFinder.getSeats(showId);
    };
}
