package pl.com.bottega.cms.infrastructure;

import javassist.NotFoundException;
import org.springframework.stereotype.Component;
import pl.com.bottega.cms.model.Show;
import pl.com.bottega.cms.model.repositories.ShowRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Component
public class JPAShowRepository implements ShowRepository {

    private EntityManager entityManager;

    public JPAShowRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Show show) {
        entityManager.persist(show);
    }

    @Override
    public Show getShow(Long showId) {
        try {
            Show show = (Show) entityManager.createQuery("FROM Show s WHERE s.id = :id")
                .setParameter("id", showId)
                .getSingleResult();
            return show;
        } catch (NoResultException ex) {
            throw new NoSuchEntityException();
        }
    }
}
