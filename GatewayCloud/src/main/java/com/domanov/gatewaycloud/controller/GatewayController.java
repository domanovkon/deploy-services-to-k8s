package com.domanov.gatewaycloud.controller;

import com.domanov.gatewaycloud.dto.*;
import com.domanov.gatewaycloud.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("https://lab2-gateway-service.herokuapp.com/api/v1")
@RequestMapping("api/v1")
public class GatewayController {

    @Autowired
    GatewayService gatewayService;

    @GetMapping("/privilege")
    @CrossOrigin(origins = "*")
    public BonusResponseDTO getBonusAccountPrivileges(@RequestHeader("X-User-Name") String username) throws IOException, InterruptedException {
        return gatewayService.getBonusAccountPrivileges(username);
    }

    @GetMapping(value = "/flights", params = {"page", "size"})
    @CrossOrigin(origins = "*")
    public FlightPageResponseDTO getFlightsPage(@RequestParam("page") int page, @RequestParam("size") int size) throws IOException, InterruptedException {
        return gatewayService.findAll(page, size);
    }

    @PostMapping("/tickets")
    @CrossOrigin(origins = "*")
    public FlightBuyResponseDTO buyTicket(@RequestHeader("X-User-Name") String username, @RequestBody FlightBuyRequest flightBuyRequest) throws IOException, InterruptedException {
        return gatewayService.buyTicket(username, flightBuyRequest);
    }

    @GetMapping("/tickets/{ticketUid}")
    @CrossOrigin(origins = "*")
    public TicketInformationDTO getTicket(@PathVariable(value = "ticketUid") UUID ticketUid, @RequestHeader("X-User-Name") String username) throws IOException, InterruptedException {
        return gatewayService.getTicket(ticketUid, username);
    }

    @GetMapping("/tickets")
    @CrossOrigin(origins = "*")
    public List<TicketInformationDTO> getTickets(@RequestHeader("X-User-Name") String username) throws IOException, InterruptedException {
        return gatewayService.getTickets(username);
    }

    @GetMapping("/me")
    @CrossOrigin(origins = "*")
    public UserInformationDTO getUser(@RequestHeader("X-User-Name") String username) throws IOException, InterruptedException {
        return gatewayService.getUser(username);
    }

    @DeleteMapping("tickets/{ticketUid}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<TicketInformationDTO> ticketCancel(@PathVariable(value = "ticketUid") UUID ticketUid, @RequestHeader("X-User-Name") String username) throws IOException, InterruptedException {
        return gatewayService.ticketCancel(ticketUid, username);
    }
}
