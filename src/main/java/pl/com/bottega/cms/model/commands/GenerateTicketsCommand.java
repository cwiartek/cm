package pl.com.bottega.cms.model.commands;

public class GenerateTicketsCommand implements Command {

    private Long reservationNumber;

    public Long getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(Long reservationNumber) {
        this.reservationNumber = reservationNumber;
    }
}
