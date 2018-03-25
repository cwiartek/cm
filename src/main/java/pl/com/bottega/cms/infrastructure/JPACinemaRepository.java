package pl.com.bottega.cms.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.cms.model.Cinema;
import pl.com.bottega.cms.model.Movie;
import pl.com.bottega.cms.model.commands.CreateCinemaCommand;
import pl.com.bottega.cms.model.repositories.CinemaRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
public class JPACinemaRepository implements CinemaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public JPACinemaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Cinema cinema) {
        entityManager.persist(cinema);
    }

    @Override
    public boolean isOccupied(CreateCinemaCommand cmd) {
        return getCinema(cmd).isPresent();
    }

    @Override
    public Cinema get(Long cinemaId) {
        Cinema cinema = entityManager.find(Cinema.class, cinemaId);
        if (cinema == null) {
            throw new NoSuchEntityException();
        }
        return cinema;
    }

    private Optional<Cinema> getCinema(CreateCinemaCommand cmd) {
        try {
            Cinema cinema= (Cinema) entityManager.createQuery("FROM Cinema c WHERE c.name = :name AND c.city = :city")
                    .setParameter("name", cmd.getName() ).setParameter("city", cmd.getCity()).getSingleResult();
            return Optional.of(cinema);
        }
        catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
