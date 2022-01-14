package com.domanov.flightservice.service;

import com.domanov.flightservice.dto.FlightDTO;
import com.domanov.flightservice.dto.FlightPageDTO;
import com.domanov.flightservice.model.Flight;
import com.domanov.flightservice.repository.AirportRepository;
import com.domanov.flightservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service("FlightService")
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;


    public FlightPageDTO findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Flight> flightsPage = flightRepository.findAll(pageable);

        FlightPageDTO flightPageDTO = new FlightPageDTO();
        flightPageDTO.setPage(page);
        flightPageDTO.setPageSize(flightsPage.getNumberOfElements());
        flightPageDTO.setTotalElements(flightsPage.getTotalElements());

        List<FlightDTO> flightDTOList = new ArrayList<>();
        FlightDTO flightDTO = new FlightDTO();
        for (Flight fl : flightsPage.getContent()) {
            flightDTO.setFlightNumber(fl.getFlightNumber());
            flightDTO.setFromAirport(fl.getFromAirport().getCity() + " " + fl.getFromAirport().getName());
            flightDTO.setToAirport(fl.getToAirport().getCity() + " " + fl.getToAirport().getName());
            flightDTO.setDate(fl.getDatetime().toLocalDateTime());
            flightDTO.setDate(fl.getDatetime().toLocalDateTime().minus(Duration.ofSeconds(fl.getDatetime().getOffset().getTotalSeconds())));
            flightDTO.setPrice(fl.getPrice());
            flightDTOList.add(flightDTO);
        }
        flightPageDTO.setFlightDTO(flightDTOList);

        return flightPageDTO;
    }

    public FlightDTO findTicket(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber);
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFromAirport(flight.getFromAirport().getCity() + " " + flight.getFromAirport().getName());
        flightDTO.setToAirport(flight.getToAirport().getCity() + " " + flight.getToAirport().getName());
        flightDTO.setDate(flight.getDatetime().toLocalDateTime().minus(Duration.ofSeconds(flight.getDatetime().getOffset().getTotalSeconds())));
        flightDTO.setPrice(flight.getPrice());
        return flightDTO;
    }
}
