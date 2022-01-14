package com.domanov.ticketservice.controller;

import com.domanov.ticketservice.dto.FlightBuyRequest;
import com.domanov.ticketservice.dto.TicketDTO;
import com.domanov.ticketservice.model.Ticket;
import com.domanov.ticketservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("https://lab3-ticket-service.herokuapp.com/api/v1/tickets")
@RequestMapping("api/v1/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;

    // Покупка билета
    @PostMapping("")
    @CrossOrigin(origins = "*")
    public Ticket buyTicket(@RequestHeader("X-User-Name") String username, @RequestBody FlightBuyRequest flightBuyRequest) {
        return ticketService.buyTicket(username, flightBuyRequest);
    }

    // Информация по всем билетам
    @GetMapping()
    @CrossOrigin(origins = "*")
    public List<TicketDTO> getTickets(@RequestHeader("X-User-Name") String username) {
        return ticketService.getTickets(username);
    }

    // Информация по конкретному билету
    @GetMapping("/{ticketUid}")
    @CrossOrigin(origins = "*")
    public TicketDTO getTicket(@PathVariable(value = "ticketUid") UUID ticketUid, @RequestHeader("X-User-Name") String username) {
        return ticketService.getTicket(ticketUid, username);
    }

    // Возврат билета
    @DeleteMapping("/{ticketUid}")
    @CrossOrigin(origins = "*")
    public Ticket ticketCancel(@PathVariable(value = "ticketUid") UUID ticketUid, @RequestHeader("X-User-Name") String username) {
        return ticketService.ticketCancel(ticketUid, username);
    }
}
