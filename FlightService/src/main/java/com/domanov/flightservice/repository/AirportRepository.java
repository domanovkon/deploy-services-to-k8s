package com.domanov.flightservice.repository;

import com.domanov.flightservice.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
}
