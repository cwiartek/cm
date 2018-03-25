package pl.com.bottega.cms.model;

import pl.com.bottega.cms.model.commands.CalculatePricesCommand;
import pl.com.bottega.cms.model.commands.SetTicketPricesCommand;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketPrieces {

    CalculatePricesCommand cmd;

    public TicketPrieces(CalculatePricesCommand cmd) {
        this.cmd = cmd;
    }

    Receipt calculate(Map<String,BigDecimal> prices){

        BigDecimal price = cmd.getTickets().stream().map(ticket -> ticket.getPrice(prices))
                             .collect(Collectors.toList()).stream().reduce(BigDecimal.ZERO,BigDecimal::add);
        Receipt receipt = new Receipt();
        receipt.setTotalPrice(price);

        Set<ReceiptLine> tickets = cmd.getTickets().stream().map(ticket -> new ReceiptLine(ticket, prices)).collect(Collectors.toSet());
        receipt.setTickets(tickets);

        return receipt;
    }
}
