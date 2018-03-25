package pl.com.bottega.cms.model;

import pl.com.bottega.cms.model.commands.CreateCinemaCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Cinema {
    public Cinema(){}

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String city;

    public Cinema(CreateCinemaCommand cmd) {
        this.name=cmd.getName();
        this.city=cmd.getCity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
