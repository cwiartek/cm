package pl.com.bottega.cms.application;

import pl.com.bottega.cms.model.Seat;
import pl.com.bottega.cms.model.Show;

import java.util.Collection;

public interface Pdf {
    byte[] getDocumentAsBytes(Collection<Seat> seats, Show show);
}
