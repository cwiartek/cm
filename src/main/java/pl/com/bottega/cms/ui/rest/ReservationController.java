package pl.com.bottega.cms.ui.rest;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.cms.application.CommandGateway;
import pl.com.bottega.cms.application.ReservationDto;
import pl.com.bottega.cms.model.commands.CreateReservationCommand;
import pl.com.bottega.cms.model.Receipt;
import pl.com.bottega.cms.model.commands.CalculatePricesCommand;
import pl.com.bottega.cms.model.commands.GenerateTicketsCommand;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ReservationController {

    private CommandGateway gateway;

    public ReservationController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @PutMapping("/reservations")
    public ReservationDto create(@RequestBody CreateReservationCommand cmd) {
        return gateway.execute(cmd);
    }

    @PostMapping("/price_calculator")
    public Receipt calcuatePrieces(@RequestBody CalculatePricesCommand cmd){
         return gateway.execute(cmd);
    }

    @GetMapping("/reservations/{reservationNumber}/tickets")
    public void getTickets(@PathVariable Long reservationNumber, HttpServletResponse httpServletResponse) throws IOException {
        GenerateTicketsCommand generateTicketsCommand = new GenerateTicketsCommand();
        generateTicketsCommand.setReservationNumber(reservationNumber);
        byte[] pdfData = gateway.execute(generateTicketsCommand);
        streamPdf(httpServletResponse, pdfData, String.format("reservation_%d.pdf", reservationNumber));
    }

    protected void streamPdf(HttpServletResponse response, byte[] data, String name) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + name);
        response.setContentLength(data.length);

        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    }
}
