package com.domanov.flightservice.model;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "flight", schema = "flights")
public class Flight implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "flight_number", length = 20, nullable = false)
    private String flightNumber;

    @Column(name = "datetime", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Type(type = "java.time.ZonedDateTime")
    private ZonedDateTime datetime;

    @ManyToOne
    @JoinColumn(name = "from_airport_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Airport fromAirport;

    @ManyToOne
    @JoinColumn(name = "to_airport_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Airport toAirport;

    @Column(name = "price", nullable = false)
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public Airport getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(Airport fromAirport) {
        this.fromAirport = fromAirport;
    }

    public Airport getToAirport() {
        return toAirport;
    }

    public void setToAirport(Airport toAirport) {
        this.toAirport = toAirport;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

