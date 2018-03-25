package pl.com.bottega.cms.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.cms.model.Reservation;
import pl.com.bottega.cms.model.repositories.ReservationRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JPAReservationRepository implements ReservationRepository {

    private EntityManager entityManager;

    public JPAReservationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Reservation reservation) {
        entityManager.persist(reservation);
    }

    @Override
    public Set<Reservation> getReservations(Long showId) {
        List<Reservation> reservationList = entityManager.createQuery("SELECT r FROM Reservation r WHERE r.showId = :show_id")
            .setParameter("show_id", showId)
            .getResultList();

        return reservationList.stream().collect(Collectors.toSet());
    }

    @Override
    public Reservation get(Long resevationId) {
        Reservation reservation = (Reservation) entityManager.createQuery("SELECT r FROM Reservation r WHERE r.id = :id")
            .setParameter("id", resevationId)
            .getSingleResult();

        return reservation;
    }
}
