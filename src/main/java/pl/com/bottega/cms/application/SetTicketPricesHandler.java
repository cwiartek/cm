package pl.com.bottega.cms.application;

import org.springframework.stereotype.Component;
import pl.com.bottega.cms.model.Movie;
import pl.com.bottega.cms.model.TicketPrieces;
import pl.com.bottega.cms.model.commands.Command;
import pl.com.bottega.cms.model.commands.SetTicketPricesCommand;
import pl.com.bottega.cms.model.repositories.MovieRepository;

import javax.transaction.Transactional;

@Component
public class SetTicketPricesHandler implements Handler<SetTicketPricesCommand, Void> {

    private MovieRepository movieRepository;

    public SetTicketPricesHandler (MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Transactional
    public Void handle(SetTicketPricesCommand cmd){
        Movie movie = movieRepository.get(cmd.getMovieId());
        movie.setPrices(cmd.getPrices());
        movieRepository.save(movie);
        return null;
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return SetTicketPricesCommand.class;
    }
}
