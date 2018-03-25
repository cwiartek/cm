package pl.com.bottega.cms.model;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Map;


public class ReceiptLine {
    String kind;
    Integer count;
    BigDecimal unitPrice;
    BigDecimal total;

    public ReceiptLine(Ticket ticket, Map<String,BigDecimal> prices) {
        this.kind = ticket.getKind();
        this.count = ticket.getCount();
        this.unitPrice = ticket.getUnitPrice(ticket.getKind(), prices);
        this.total = ticket.getPrice(prices);
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
