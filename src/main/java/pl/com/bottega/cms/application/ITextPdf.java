package pl.com.bottega.cms.application;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.DottedBorder;
import com.itextpdf.layout.borders.RidgeBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import pl.com.bottega.cms.model.Seat;
import pl.com.bottega.cms.model.Show;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ITextPdf implements Pdf {

    @Override
    public byte[] getDocumentAsBytes(Collection<Seat> seats, Show show) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        Collection<SeatDto> seatDtos = new TreeSet<>();
        seats.stream().forEach(seat -> seatDtos.add(new SeatDto(seat)));
        seatDtos.stream().forEach(seatDto -> document.add(getTicketTable(pdfDocument, seatDto, show)));

        document.close();
        return outputStream.toByteArray();
    }

    private Table getTicketTable(PdfDocument pdfDocument, SeatDto seat, Show show) {
        Table table = new Table(2);
        table.setWidth(pdfDocument.getDefaultPageSize().getWidth() - 80);
        table.setAutoLayout();
        table.setMarginTop(5);
        Cell cell;
        cell = new Cell().add(new Paragraph(String.format("Title: %s", show.getMovie().getTitle())));
        cell.add(new Paragraph(String.format("When: %s", show.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s")))));
        cell.setBorder(new DottedBorder(1));
        cell.setWidth(300);
        cell.setPadding(5);
        table.addCell(cell);

        cell = new Cell().add(new Paragraph(String.format("Row: %d\nSeat: %d", seat.getRow(), seat.getSeat())));
        cell.setBorder(new DottedBorder(1));
        cell.setPadding(5);
        table.addCell(cell);
        return table;
    }
}
