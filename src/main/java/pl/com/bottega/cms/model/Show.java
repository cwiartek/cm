package pl.com.bottega.cms.model;

import org.springframework.format.annotation.DateTimeFormat;
import pl.com.bottega.cms.model.commands.CalculatePricesCommand;
import pl.com.bottega.cms.model.commands.CreateReservationCommand;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany
    @JoinColumn(name = "show_id")
    private Collection<Reservation> reservations = new LinkedList<>();

    private LocalDateTime date;

    public Show() {};

    public Show(Cinema cinema, Movie movie, LocalDateTime date) {
        this.cinema = cinema;
        this.movie = movie;
        this.date = date;
    }

    public Receipt calculate(CalculatePricesCommand cmd){
        return this.movie.calculate(cmd);
    }

    public Movie getMovie() {
        return movie;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isTakePlaceInDate(LocalDate date) {
        return this.getDate().toLocalDate().equals(date);
    }

    public void checkTicketPrices(CreateReservationCommand cmd) {
        movie.checkTicketPrices(cmd);
    }
}
