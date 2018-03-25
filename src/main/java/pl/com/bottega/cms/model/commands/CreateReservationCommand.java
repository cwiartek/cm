package pl.com.bottega.cms.model.commands;

import pl.com.bottega.cms.model.CinemaHall;
import pl.com.bottega.cms.model.Customer;
import pl.com.bottega.cms.model.Seat;
import pl.com.bottega.cms.model.Ticket;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateReservationCommand implements Command {
    Long showId;
    Customer customer;
    Set<Ticket> tickets;
    Set<Seat> seats;

    public void validate(ValidationErrors errors) {
        validatePresence(errors, "tickets", tickets);
        validateUniqueTicketsKind(errors, "tickets", tickets);
        validateTicketAmount(errors, "tickets", tickets);
        validateSeats(errors, "seats", seats);
        validateCustomer(errors, "customer", customer);
    }

    private void validateCustomer(ValidationErrors errors, String field, Customer customer) {
        validatePresence(errors, field, customer);
        if (customer != null) {
            validatePresence(errors, "firstName", customer.getFirstName());
            validatePresence(errors, "lastName", customer.getLastName());
            validatePresence(errors, "email", customer.getEmail());
            validateEmail(errors, "email", customer.getEmail());
            validatePresence(errors, "phone", customer.getPhone());
        }
    }

    private void validateTicketAmount(ValidationErrors errors, String field, Set<Ticket> value) {
        try {
            if (value != null && value.stream().anyMatch(ticket -> ticket.getCount() <= 0)) {
                errors.add(field, "count must be greater than zero");
            }
        } catch (NullPointerException ex) {
            errors.add(field, "can't contain null values");
        }
    }

    private void validateUniqueTicketsKind(ValidationErrors errors, String field, Set<Ticket> value) {
        if (value == null) {
            errors.add(field, "can't be blank");
        }
        List<String> unique = value.stream().map(ticket -> ticket.getKind()).distinct().collect(Collectors.toList());
        if (value != null && value.size() != unique.size()) {
            errors.add(field, "tickets is not unique");
        }
    }

    private void validateSeats(ValidationErrors errors, String field, Set<Seat> value) {
        if (value == null || value.size() == 0) {
            errors.add(field, "can't be empty");
        }
        try {
            if (value != null && value.stream().anyMatch(seat -> (seat.getSeat() < 0 || seat.getRow() < 0))) {
                errors.add(field, "seats and rows must be greater than zero");
            }
            if (value != null && value.stream().anyMatch(seat -> (seat.getSeat() >= CinemaHall.SEATS || seat.getRow() >= CinemaHall.ROWS))) {
                errors.add(field, "bad number of seat or row");
            }
        } catch (NullPointerException ex) {
            errors.add(field, "can't contain null values");
        }
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }
}