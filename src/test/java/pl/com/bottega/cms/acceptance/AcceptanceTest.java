package pl.com.bottega.cms.acceptance;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AcceptanceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionTemplate tt;

    @After
    public void cleanUp() {
        tt.execute((c) -> {
            em.createNativeQuery("DELETE FROM cinema").executeUpdate();
            em.createNativeQuery("TRUNCATE cinema").executeUpdate();
            em.createNativeQuery("DELETE FROM movie").executeUpdate();
            em.createNativeQuery("TRUNCATE movie").executeUpdate();
            em.createNativeQuery("DELETE FROM movie_actors").executeUpdate();
            em.createNativeQuery("DELETE FROM movie_genres").executeUpdate();
            em.createNativeQuery("DELETE FROM shows").executeUpdate();
            em.createNativeQuery("TRUNCATE shows").executeUpdate();
            em.createNativeQuery("DELETE FROM reservation").executeUpdate();
            em.createNativeQuery("TRUNCATE reservation").executeUpdate();
            em.createNativeQuery("DELETE FROM ticket").executeUpdate();
            em.createNativeQuery("TRUNCATE ticket").executeUpdate();
            em.createNativeQuery("DELETE FROM seat").executeUpdate();
            em.createNativeQuery("TRUNCATE seat").executeUpdate();
            em.createNativeQuery("DELETE FROM ticket_prices").executeUpdate();
            return null;
        });
    }

}
