package pl.com.bottega.cms.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.cms.infrastructure.JPACinemaRepository;
import pl.com.bottega.cms.model.Cinema;
import pl.com.bottega.cms.model.commands.CreateCinemaCommand;
import pl.com.bottega.cms.model.repositories.CinemaRepository;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateCinemaHandlerTest {

//    @Autowired
//    private EntityManager entityManager;

    //    private CinemaRepository cinemaRepository = new JPACinemaRepository(entityManager);
    private CreateCinemaHandler cinemaHandler;

    @Mock
    private CinemaRepository cinemaRepository;

    @Mock
    Cinema cinema;

    @Before
    public void setUp() {
        cinemaHandler = new CreateCinemaHandler(cinemaRepository);
    }

    @Test
    @Transactional
    public void handle() {
        //given
        CreateCinemaCommand command = new CreateCinemaCommand();
        command.setName("name");
        command.setCity("city");
        //when
        when(cinemaRepository.isOccupied(command)).thenReturn(false);
        cinemaHandler.handle(command);
        //then
//       verify(cinemaRepository).save(cinema);
    }

    @Test
    public void getSupportedCommandClass() {
    }
}