package pl.com.bottega.cms.application;


//import com.sun.xml.internal.ws.api.ha.StickyFeature;
//import pl.com.bottega.cms.model.Cinema;

public class CinemaDto {

    private Long id;
    private String name;
    private String city;

    public CinemaDto(Long id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
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
