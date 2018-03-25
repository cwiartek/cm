package pl.com.bottega.cms.model.repositories;

import pl.com.bottega.cms.model.Show;

public interface ShowRepository {
    void save(Show show);

    Show getShow(Long showId);
}
