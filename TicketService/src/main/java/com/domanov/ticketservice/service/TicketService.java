package com.domanov.ticketservice.service;

import com.domanov.ticketservice.dto.FlightBuyRequest;
import com.domanov.ticketservice.dto.TicketDTO;
import com.domanov.ticketservice.model.Ticket;
import com.domanov.ticketservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("TicketService")
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<TicketDTO> getTickets(String username) {
        List<Ticket> ticketList = ticketRepository.findByUsername(username);

        List<TicketDTO> ticketDTOList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setTicketUid(ticket.getTicket_uid());
            ticketDTO.setFlightNumber(ticket.getFlightNumber());
            ticketDTO.setPrice(ticket.getPrice());
            ticketDTO.setStatus(ticket.getStatus());
            ticketDTOList.add(ticketDTO);
        }
        return ticketDTOList;
    }

    public TicketDTO getTicket(UUID ticketUid, String username) {
        Ticket ticket = ticketRepository.findByUsernameAndTicketUid(username, ticketUid);
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketUid(ticket.getTicket_uid());
        ticketDTO.setFlightNumber(ticket.getFlightNumber());
        ticketDTO.setPrice(ticket.getPrice());
        ticketDTO.setStatus(ticket.getStatus());
        return ticketDTO;
    }

    public Ticket ticketCancel(UUID ticketUid, String username) {
        Ticket ticket = ticketRepository.findByUsernameAndTicketUid(username, ticketUid);
        ticket.setStatus("CANCELED");
        ticketRepository.save(ticket);
        return ticket;
    }

    public Ticket buyTicket(String username, FlightBuyRequest flightBuyRequest) {
        Ticket ticket = new Ticket(username, flightBuyRequest.getFlightNumber(), flightBuyRequest.getPrice(), "PAID");
        ticketRepository.save(ticket);
        return ticket;
    }

}
