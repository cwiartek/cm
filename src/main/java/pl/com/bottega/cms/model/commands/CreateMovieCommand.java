package pl.com.bottega.cms.model.commands;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CreateMovieCommand implements Command {
    private String title;
    private String description;
    private Set<String> actors = new HashSet<>();
    private Set<String> genres = new HashSet<>();
    private Integer minAge;
    private Integer length;
    private Map<String, BigDecimal> prices;

    public void validate(ValidationErrors errors){
        validatePresence(errors,"title", title);
        validatePresence(errors,"description", description);
        validatePresence(errors,"actors", actors);
        validatePresence(errors,"genres", genres);
        validateNegativeNumber(errors,"minAge", minAge);
        validateNegativeNumber(errors,"length", length);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getActors() {
        return actors;
    }

    public void setActors(Set<String> actors) {
        this.actors = actors;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Map<String,BigDecimal> getPrices() {
        return prices;
    }
}
