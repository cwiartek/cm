package pl.com.bottega.cms.application;

import pl.com.bottega.cms.model.Reservation;

public class ReservationDto {

    Long reservationNumber;

    public ReservationDto(Reservation reservation) {
        this.reservationNumber = reservation.getId();
    }

    public Long getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(Long reservationNumber) {
        this.reservationNumber = reservationNumber;
    }
}
