package pl.com.bottega.cms.application;

import pl.com.bottega.cms.model.Seat;

public class SeatDto implements Comparable<SeatDto> {
    private int row;
    private int seat;

    public SeatDto(Seat seat) {
        this.row = seat.getRowToShow();
        this.seat = seat.getSeatToShow();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    @Override
    public int compareTo(SeatDto seatDto) {
        int rowResult = row - seatDto.row;
        if (rowResult < 0) {
            return -1;
        }
        if (rowResult > 0) {
            return 1;
        }
        return seat - seatDto.seat;
    }
}
