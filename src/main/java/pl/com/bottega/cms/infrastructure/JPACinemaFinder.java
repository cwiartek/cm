package pl.com.bottega.cms.infrastructure;

        import org.springframework.stereotype.Component;
        import pl.com.bottega.cms.application.CinemaDto;
        import pl.com.bottega.cms.application.CinemaFinder;
        import pl.com.bottega.cms.application.CinemaHallDto;
        import pl.com.bottega.cms.model.CinemaHall;
        import pl.com.bottega.cms.model.Reservation;
        import pl.com.bottega.cms.model.repositories.ReservationRepository;
        import pl.com.bottega.cms.model.repositories.ShowRepository;

        import javax.persistence.EntityManager;
        import java.util.List;
        import java.util.Set;

@Component
public class JPACinemaFinder implements CinemaFinder {

    public JPACinemaFinder(EntityManager entityManager, ReservationRepository reservationRepository, ShowRepository showRepository) {
        this.entityManager = entityManager;
        this.reservationRepository = reservationRepository;
        this.showRepository = showRepository;
    }

    private EntityManager entityManager;

    private ReservationRepository reservationRepository;

    private ShowRepository showRepository;

    public List<CinemaDto> getAll() {
        List<CinemaDto> resultList= entityManager.createQuery("SELECT NEW pl.com.bottega.cms.application.CinemaDto(c.id, c.name,c.city) FROM Cinema c").getResultList();
        return resultList;
    }

    public CinemaHallDto getSeats(Long showId) {
        showRepository.getShow(showId);
        Set<Reservation> currentReservations = reservationRepository.getReservations(showId);
        CinemaHall cinemaHall = new CinemaHall(currentReservations);
        CinemaHallDto cinemaHallDto = new CinemaHallDto(cinemaHall);
        return cinemaHallDto;
    }
}
