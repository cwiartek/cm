package pl.com.bottega.cms.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.cms.model.CinemaHall;
import pl.com.bottega.cms.model.Reservation;
import pl.com.bottega.cms.model.Show;
import pl.com.bottega.cms.model.commands.Command;
import pl.com.bottega.cms.model.commands.CreateReservationCommand;
import pl.com.bottega.cms.model.repositories.ReservationRepository;
import pl.com.bottega.cms.model.repositories.ShowRepository;

import java.util.Set;

@Component
public class CreateReservationHandler implements Handler<CreateReservationCommand, ReservationDto> {

    private ReservationRepository reservationRepository;

    private ShowRepository showRepository;

    public CreateReservationHandler(ReservationRepository reservationRepository, ShowRepository showRepository) {
        this.reservationRepository = reservationRepository;
        this.showRepository = showRepository;
    }

    @Transactional
    public ReservationDto handle(CreateReservationCommand cmd) {
        Show show = showRepository.getShow(cmd.getShowId());
        show.checkTicketPrices(cmd);
        Set<Reservation> currentReservations = reservationRepository.getReservations(cmd.getShowId());
        CinemaHall cinemaHall = new CinemaHall(currentReservations);
        cinemaHall.checkReservation(cmd);
        Reservation reservation = new Reservation(cmd);
        reservationRepository.save(reservation);
        return new ReservationDto(reservation);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return CreateReservationCommand.class;
    }
}
