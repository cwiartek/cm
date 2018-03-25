package pl.com.bottega.cms.application;

import java.util.List;

public interface CinemaFinder {
    List<CinemaDto> getAll();
    CinemaHallDto getSeats(Long showId);
}
