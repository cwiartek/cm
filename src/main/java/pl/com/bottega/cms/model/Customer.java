package pl.com.bottega.cms.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    String firstName;

    @Column
    String lastName;

    @Column
    String email;
    
    @Column
    String phone;

    @OneToMany
    @JoinColumn(name = "reservation_id")
    private Collection<Ticket> reservations = new LinkedList<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
