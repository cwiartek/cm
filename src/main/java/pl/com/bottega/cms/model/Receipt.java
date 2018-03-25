package pl.com.bottega.cms.model;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Set;


public class Receipt {

    Set<ReceiptLine> tickets;

    BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<ReceiptLine> getTickets() {
        return tickets;
    }

    public void setTickets(Set<ReceiptLine> tickets) {
        this.tickets = tickets;
    }
}
