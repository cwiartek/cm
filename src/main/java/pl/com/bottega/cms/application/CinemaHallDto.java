package pl.com.bottega.cms.application;

import pl.com.bottega.cms.model.CinemaHall;
import pl.com.bottega.cms.model.Seat;

import java.util.*;

public class CinemaHallDto {
    Set<SeatDto> free = new TreeSet<>();
    Set<SeatDto> occupied = new TreeSet<>();

    public CinemaHallDto(CinemaHall cinemaHall) {
        for (int row = 0; row < CinemaHall.ROWS; row++) {
            for (int seat = 0; seat < CinemaHall.SEATS; seat++) {
                int rowNumber = row + 1;
                int seatNumber = seat + 1;
                SeatDto seatDto = new SeatDto(new Seat(rowNumber, seatNumber));
                if (cinemaHall.isOccupied(row, seat)) {
                    occupied.add(seatDto);
                } else {
                    free.add(seatDto);
                }
            }
        }

    }

    public Set<SeatDto> getFree() {
        return free;
    }

    public void setFree(Set<SeatDto> free) {
        this.free = free;
    }

    public Set<SeatDto> getOccupied() {
        return occupied;
    }

    public void setOccupied(Set<SeatDto> occupied) {
        this.occupied = occupied;
    }
}
