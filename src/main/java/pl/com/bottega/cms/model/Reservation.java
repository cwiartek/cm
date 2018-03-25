package pl.com.bottega.cms.model;

import pl.com.bottega.cms.model.commands.CreateReservationCommand;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    Long id;

    @Column(name = "show_id")
    private Long showId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private Collection<Seat> seats = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private Collection<Ticket> tickets = new LinkedList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation(CreateReservationCommand cmd) {
        this.showId = cmd.getShowId();
        this.customer = cmd.getCustomer();
        this.seats = cmd.getSeats();
        this.tickets = cmd.getTickets();
        this.status = ReservationStatus.PENDING;
    }

    public Reservation() {}

    public Collection<Seat> getSeats() {
        return seats;
    }

    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public Long getId() {
        return id;
    }

    public Long getShowId() {
        return showId;
    }
}
