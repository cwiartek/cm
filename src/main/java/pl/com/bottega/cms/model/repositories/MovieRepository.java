package pl.com.bottega.cms.model.repositories;

import pl.com.bottega.cms.model.Movie;

public interface MovieRepository {
    void save(Movie movie);
    Movie get(Long movieId);
}
