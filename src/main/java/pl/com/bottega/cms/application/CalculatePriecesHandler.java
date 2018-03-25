package pl.com.bottega.cms.application;

import org.springframework.stereotype.Component;
import pl.com.bottega.cms.infrastructure.IncorrectTicketException;
import pl.com.bottega.cms.infrastructure.NoSuchEntityException;
import pl.com.bottega.cms.model.Receipt;
import pl.com.bottega.cms.model.Show;
import pl.com.bottega.cms.model.Ticket;
import pl.com.bottega.cms.model.commands.CalculatePricesCommand;
import pl.com.bottega.cms.model.commands.Command;
import pl.com.bottega.cms.model.commands.CommandInvalidException;
import pl.com.bottega.cms.model.commands.ValidationErrors;
import pl.com.bottega.cms.model.repositories.MovieRepository;
import pl.com.bottega.cms.model.repositories.ShowRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Component
public class CalculatePriecesHandler implements Handler<CalculatePricesCommand, Receipt>{

    private ShowRepository showRepository;
    private MovieRepository movieRepository;

    public CalculatePriecesHandler(ShowRepository showRepository, MovieRepository movieRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
   public Receipt handle(CalculatePricesCommand cmd){
        Show show;
        Receipt receipt;
        try{
            show = showRepository.getShow(cmd.getShowId());
            receipt = show.calculate(cmd);
        } catch (NoSuchEntityException ex){
            ValidationErrors errors = new ValidationErrors();
            errors.add("show", "is not exist");
            throw new CommandInvalidException(errors);
        } catch (IncorrectTicketException ex){
            ValidationErrors errors = new ValidationErrors();
            errors.add("tikets", "Incorrect ticket type");
            throw new CommandInvalidException(errors);
        }
        return receipt;
    }

    public void validateTicketType(CalculatePricesCommand cmd){
        Set<Ticket> tickets = cmd.getTickets();

        for(Ticket ticketTest : tickets){
            if(ticketTest.getKind().equals(movieRepository));

        }

    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return CalculatePricesCommand.class;
    }
}
