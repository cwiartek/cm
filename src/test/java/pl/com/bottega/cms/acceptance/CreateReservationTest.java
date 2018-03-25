package pl.com.bottega.cms.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.cms.application.CommandGateway;
import pl.com.bottega.cms.model.Customer;
import pl.com.bottega.cms.model.Reservation;
import pl.com.bottega.cms.model.Seat;
import pl.com.bottega.cms.model.Ticket;
import pl.com.bottega.cms.model.commands.*;
import pl.com.bottega.cms.model.repositories.ReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateReservationTest extends AcceptanceTest {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void shouldCreateReservation() {
        commandGateway.execute(getDefaultCmd());

        Set<Reservation> reservations = reservationRepository.getReservations(1L);
        assertEquals(reservations.size(), 1);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateTheSameReservation() {
        commandGateway.execute(getDefaultCmd());
        commandGateway.execute(getDefaultCmd());
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateReservationWithBadTypeTickets() {
        CreateReservationCommand createReservationCommand = getDefaultCmd();

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket("trzy", 1));

        createReservationCommand.setTickets(tickets);
        commandGateway.execute(createReservationCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateReservationWithEmptyTickets() {
        CreateReservationCommand createReservationCommand = getDefaultCmd();

        Set<Ticket> tickets = new HashSet<>();

        createReservationCommand.setTickets(tickets);
        commandGateway.execute(createReservationCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateReservationWithBadAmountTicket() {
        CreateReservationCommand createReservationCommand = getDefaultCmd();

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket("jeden", -1));

        createReservationCommand.setTickets(tickets);
        commandGateway.execute(createReservationCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateReservationWithTicketWithoutCount() {
        CreateReservationCommand createReservationCommand = getDefaultCmd();

        Set<Ticket> tickets = new HashSet<>();
        Ticket ticket = new Ticket();
        ticket.setKind("jeden");
        tickets.add(ticket);

        createReservationCommand.setTickets(tickets);
        commandGateway.execute(createReservationCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateReservationWithTicketWithoutKind() {
        CreateReservationCommand createReservationCommand = getDefaultCmd();

        Set<Ticket> tickets = new HashSet<>();
        Ticket ticket = new Ticket();
        ticket.setCount(2);
        tickets.add(ticket);

        createReservationCommand.setTickets(tickets);
        commandGateway.execute(createReservationCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateReservationWithEmptySeats() {
        CreateReservationCommand createReservationCommand = getDefaultCmd();

        Set<Seat> seats = new HashSet<>();

        createReservationCommand.setSeats(seats);
        commandGateway.execute(createReservationCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateReservationWithSeatWithoutRow() {
        CreateReservationCommand createReservationCommand = getDefaultCmd();

        Set<Seat> seats = new HashSet<>();
        Seat seat = new Seat();
        seat.setSeat(1);
        seats.add(seat);

        createReservationCommand.setSeats(seats);
        commandGateway.execute(createReservationCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateReservationWithSeatWithoutSeat() {
        CreateReservationCommand createReservationCommand = getDefaultCmd();

        Set<Seat> seats = new HashSet<>();
        Seat seat = new Seat();
        seat.setRow(1);
        seats.add(seat);

        createReservationCommand.setSeats(seats);
        commandGateway.execute(createReservationCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateReservationWithSeatBadSeats() {
        CreateReservationCommand createReservationCommand = getDefaultCmd();

        Set<Seat> seats = new HashSet<>();
        seats.add(new Seat(2, 2));
        seats.add(new Seat(2, 3));
        seats.add(new Seat(2, 5));

        createReservationCommand.setSeats(seats);
        commandGateway.execute(createReservationCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateReservationWithDuplicateTickets() {
        CreateReservationCommand createReservationCommand = getDefaultCmd();

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket("jeden", 1));
        tickets.add(new Ticket("dwa", 1));
        tickets.add(new Ticket("jeden", 1));

        createReservationCommand.setTickets(tickets);
        commandGateway.execute(createReservationCommand);
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotCreateReservationWithEmptyCustomer() {
        CreateReservationCommand createReservationCommand = getDefaultCmd();

        Customer customer = new Customer();

        createReservationCommand.setCustomer(customer);
        commandGateway.execute(createReservationCommand);
    }

    private CreateReservationCommand getDefaultCmd() {
        createMovie();
        createCinema();
        createTicketPrices();
        createShow();

        Set<Seat> seats = new HashSet<>();
        seats.add(new Seat(2, 2));
        seats.add(new Seat(2, 3));
        seats.add(new Seat(2, 4));

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket("jeden", 1));
        tickets.add(new Ticket("dwa", 2));

        CreateReservationCommand createReservationCommand = new CreateReservationCommand();
        createReservationCommand.setCustomer(createCustomer());
        createReservationCommand.setSeats(seats);
        createReservationCommand.setTickets(tickets);
        createReservationCommand.setShowId(1L);
        return createReservationCommand;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Jan");
        customer.setLastName("Niezbędny");
        customer.setEmail("jan@niezbedny.pl");
        customer.setPhone("222333444");
        return customer;
    }

    private void createShow() {
        Set<LocalDateTime> dates = new HashSet<>();
        dates.add(LocalDateTime.parse("2017-02-02T12:22"));

        CreateShowsCommand createShowsCommand = new CreateShowsCommand();
        createShowsCommand.setDates(dates);
        createShowsCommand.setMovieId(1L);
        createShowsCommand.setCinemaId(1L);
        commandGateway.execute(createShowsCommand);
    }

    private void createTicketPrices() {
        SetTicketPricesCommand setTicketPricesCommand = new SetTicketPricesCommand();
        setTicketPricesCommand.setMovieId(1L);
        Map<String, BigDecimal> prices = new HashMap<>();
        prices.put("jeden", new BigDecimal(200.99));
        prices.put("dwa", new BigDecimal(202.99));
        prices.put("regular", new BigDecimal(2.99));
        prices.put("student", new BigDecimal(3.99));
        setTicketPricesCommand.setPrices(prices);
        commandGateway.execute(setTicketPricesCommand);
    }

    private void createCinema() {
        CreateCinemaCommand createCinemaCommand = new CreateCinemaCommand();
        createCinemaCommand.setCity("Lublin");
        createCinemaCommand.setName("Bajka");
        commandGateway.execute(createCinemaCommand);
    }

    private void createMovie() {
        Set<String> actors = new HashSet<>();
        actors.add("John");
        actors.add("Samara");

        Set<String> genres = new HashSet<>();
        genres.add("a");
        genres.add("b");

        CreateMovieCommand creteCreateMovieCommand = new CreateMovieCommand();
        creteCreateMovieCommand.setActors(actors);
        creteCreateMovieCommand.setDescription("description");
        creteCreateMovieCommand.setGenres(genres);
        creteCreateMovieCommand.setLength(200);
        creteCreateMovieCommand.setMinAge(16);
        creteCreateMovieCommand.setTitle("title");
        commandGateway.execute(creteCreateMovieCommand);
    }

}
