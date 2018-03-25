package pl.com.bottega.cms.model;

import pl.com.bottega.cms.model.commands.CommandInvalidException;
import pl.com.bottega.cms.model.commands.CreateReservationCommand;
import pl.com.bottega.cms.model.commands.ValidationErrors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CinemaHall {

    public static final int ROWS = 10;
    public static final int SEATS = 15;

    boolean[][] seats = new boolean[ROWS][SEATS];

    public CinemaHall(Set<Reservation> currentReservations) {
        currentReservations.stream().forEach(reservation -> markAsTaken(reservation));
    }

    public boolean[][] getSeats() {
        return seats;
    }

    private void markAsTaken(Reservation reservation) {
        reservation.getSeats().forEach(seat -> seats[seat.getRow()][seat.getSeat()] = true);
    }

    public void checkReservation(CreateReservationCommand cmd) {
        if (!isSeatsAvailable(cmd)) {
            throw new CommandInvalidException(
                new ValidationErrors("seats", "seats are unavailable")
            );
        }
        if (isDiffrentSizeSeatsAndTickets(cmd)) {
            throw new CommandInvalidException(
                new ValidationErrors("seats", "amount of count tickets and amount of seats must be the same")
            );
        }
        if (isPossibleToPlaceCorrectly(cmd)) {
            if (!isTheSameRows(cmd)) {
                throw new CommandInvalidException(
                    new ValidationErrors("seats", "seats are not in the same row")
                );
            }
            if (cmd.getSeats().size() != differenceMaxMinSeat(cmd)) {
                throw new CommandInvalidException(
                    new ValidationErrors("seats", "every seat must be reserved next to another")
                );
            }
        }
        if (isTooManySeats(cmd)) {
            throw new CommandInvalidException(
                new ValidationErrors("seats", "too many seats reservations")
            );
        }
    }

    private boolean isDiffrentSizeSeatsAndTickets(CreateReservationCommand cmd) {
        int numberOfSeats = cmd.getSeats().size();
        int numberOfCountTickets = cmd.getTickets().stream().mapToInt(ticket -> ticket.getCount()).sum();
        return numberOfCountTickets != numberOfSeats;
    }

    private boolean isTooManySeats(CreateReservationCommand cmd) {
        return (ROWS * SEATS) < cmd.getSeats().size();
    }

    private int differenceMaxMinSeat(CreateReservationCommand cmd) {
        Seat maxSeat = cmd.getSeats().stream().max((s1, s2) -> Integer.compare(s1.getSeat(), s2.getSeat())).get();
        Seat minSeat = cmd.getSeats().stream().min((s1, s2) -> Integer.compare(s1.getSeat(), s2.getSeat())).get();
        return maxSeat.getSeat() - minSeat.getSeat() + 1;
    }

    private boolean isTheSameRows(CreateReservationCommand cmd) {
        return new HashSet<Integer>(cmd.getSeats().stream().map(s -> s.getRow()).collect(Collectors.toSet())).size() == 1;
    }

    private boolean isSeatsAvailable(CreateReservationCommand cmd) {
        return cmd.getSeats().stream().noneMatch(seat -> seats[seat.getRow()][seat.getSeat()]);
    }

    public boolean isPossibleToPlaceCorrectly(CreateReservationCommand command) {
        Integer persons = command.getSeats().size();
        Integer counter;
        if (persons > SEATS) {
            return false;
        }
        for (boolean[] row : seats) {
            counter = 0;
            for (boolean seat : row) {
                if (!seat) {
                    counter++;
                } else {
                    counter = 0;
                }

                if (counter >= persons) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOccupied(int row, int seat) {
        return seats[row][seat];
    }
}
