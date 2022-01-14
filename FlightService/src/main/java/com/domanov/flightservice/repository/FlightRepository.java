package com.domanov.flightservice.repository;

import com.domanov.flightservice.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FlightRepository extends JpaRepository<Flight, Integer>, PagingAndSortingRepository<Flight, Integer> {

    @Query("SELECT f FROM Flight f WHERE f.flightNumber = ?1")
    Flight findByFlightNumber(String flightNumber);
}
