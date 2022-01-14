package com.domanov.flightservice.controller;

import com.domanov.flightservice.dto.FlightDTO;
import com.domanov.flightservice.dto.FlightPageDTO;
import com.domanov.flightservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin("http://localhost:8081/api/v1/flights")
@CrossOrigin("https://lab2-flight-service.herokuapp.com//api/v1/flights")
@RequestMapping("api/v1/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping(params = {"page", "size"})
    @CrossOrigin(origins = "*")
    public FlightPageDTO getFlightsPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return flightService.findAll(page, size);
    }

    @GetMapping("/{flight-number}")
    @CrossOrigin(origins = "*")
    public FlightDTO getFlight(@PathVariable(value = "flight-number") String flightNumber) {
        return flightService.findTicket(flightNumber);
    }
}
