package pl.com.bottega.cms.model.repositories;

import pl.com.bottega.cms.model.Reservation;

import java.util.Set;

public interface ReservationRepository {
    void save(Reservation reservation);
    Set<Reservation> getReservations(Long showId);
    Reservation get(Long resevationId);
}
