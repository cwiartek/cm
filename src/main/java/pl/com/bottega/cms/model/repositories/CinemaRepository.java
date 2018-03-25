package pl.com.bottega.cms.model.repositories;

import pl.com.bottega.cms.model.Cinema;
import pl.com.bottega.cms.model.commands.CreateCinemaCommand;

public interface CinemaRepository {
    void save(Cinema cinema);
    boolean isOccupied(CreateCinemaCommand cmd);
    Cinema get(Long cinemaId);
}
