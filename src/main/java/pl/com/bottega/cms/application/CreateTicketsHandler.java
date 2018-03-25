package pl.com.bottega.cms.application;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.cms.model.Reservation;
import pl.com.bottega.cms.model.Seat;
import pl.com.bottega.cms.model.Show;
import pl.com.bottega.cms.model.commands.Command;
import pl.com.bottega.cms.model.commands.GenerateTicketsCommand;
import pl.com.bottega.cms.model.repositories.ReservationRepository;
import pl.com.bottega.cms.model.repositories.ShowRepository;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

@Component
public class CreateTicketsHandler implements Handler<GenerateTicketsCommand, byte[]> {

    private ReservationRepository reservationRepository;
    private ShowRepository showRepository;

    public CreateTicketsHandler(ReservationRepository reservationRepository, ShowRepository showRepository) {
        this.reservationRepository = reservationRepository;
        this.showRepository = showRepository;
    }

    @Transactional
    public byte[] handle(GenerateTicketsCommand command) {
        Reservation reservation = reservationRepository.get(command.getReservationNumber());
        Show show = showRepository.getShow(reservation.getShowId());

        Pdf pdf = new ITextPdf();
        return pdf.getDocumentAsBytes(reservation.getSeats(), show);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return GenerateTicketsCommand.class;
    }
}
