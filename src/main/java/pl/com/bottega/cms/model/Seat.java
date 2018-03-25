package pl.com.bottega.cms.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
public class Seat {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Integer row;

    @Column(name = "seat_number")
    private Integer seat;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Seat(Integer row, Integer seat) {
        this.row = row;
        this.seat = seat;
    }

    public Seat() {}

    public Integer getRow() {
        int returnedRow = row;
        return --returnedRow;
    }

    public Integer getSeat() {
        int returnedSeat = seat;
        return --returnedSeat;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Integer getRowToShow() {
        return row;
    }

    public Integer getSeatToShow() {
        return seat;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }
}
