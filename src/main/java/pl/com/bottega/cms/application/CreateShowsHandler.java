package pl.com.bottega.cms.application;

import org.springframework.stereotype.Component;
import pl.com.bottega.cms.model.Show;
import pl.com.bottega.cms.model.ShowFactory;
import pl.com.bottega.cms.model.commands.Command;
import pl.com.bottega.cms.model.commands.CreateShowsCommand;
import pl.com.bottega.cms.model.repositories.ShowRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Component
public class CreateShowsHandler implements Handler<CreateShowsCommand, Void> {

    private ShowRepository showRepository;
    private ShowFactory showFactory;

    public CreateShowsHandler(ShowRepository showRepository, ShowFactory showFactory) {
        this.showRepository = showRepository;
        this.showFactory = showFactory;
    }

    @Transactional
    public Void handle(CreateShowsCommand command) {
        Collection<Show> shows = showFactory.createShows(command);
        for (Show show : shows) {
            showRepository.save(show);
        }
        return null;
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return CreateShowsCommand.class;
    }
}
