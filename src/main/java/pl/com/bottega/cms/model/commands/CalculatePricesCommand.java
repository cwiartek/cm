package pl.com.bottega.cms.model.commands;

import pl.com.bottega.cms.infrastructure.NoSuchEntityException;
import pl.com.bottega.cms.model.Show;
import pl.com.bottega.cms.model.Ticket;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CalculatePricesCommand implements Command {

    Long showId;
    Set<Ticket> tickets;
    List<String> listKind;


    @Override
    public void validate(ValidationErrors errors) {
        validatePresence(errors,"showId", showId);
        validateTicketPrices(errors, "tickets", tickets);
    }

    public void validateTicketPrices(ValidationErrors errors, String field, Set value) {
        if (value == null || value.size() == 0) {
            errors.add(field, "can't be empty");
        }
        try {
            if (value != null && value.stream().anyMatch(v -> v.toString().trim().length() == 0)) {
                errors.add(field, "can't be empty values");
            }
            for (Ticket ticket : tickets) {
                if (ticket.getKind() != null && ticket.getKind().isEmpty()) {
                    errors.add("Ticket", "can not be empty");
                }

                if (ticket.getKind() == null) {
                    errors.add("Ticket", "can not be null");
                }

                if (ticket.getCount() <= 0) {
                    errors.add("Count","can not be 0 and must be positive");
                }
            }
            listKind = tickets.stream().map(ticket -> ticket.getKind()).collect(Collectors.toList());
            validateDuplicatte(errors,"Ticket Kind",listKind);
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

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }


}
