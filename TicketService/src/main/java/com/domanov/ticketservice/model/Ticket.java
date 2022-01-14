package com.domanov.ticketservice.model;

import org.hibernate.annotations.Check;
import javax.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "ticket", schema = "tickets")
@Check(constraints = "status IN ('PAID', 'CANCELED')")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ticket_uid")
    private UUID ticket_uid;

    @Column(name = "username", length = 80, nullable = false)
    private String username;

    @Column(name = "flight_number", length = 20, nullable = false)
    private String flightNumber;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    public Ticket(String username, String flightNumber, Integer price, String status) {
        this.username = username;
        this.flightNumber = flightNumber;
        this.price = price;
        this.status = status;
    }

    public Ticket() {

    }

    public UUID getTicket_uid() {
        return ticket_uid;
    }

    public void setTicket_uid(UUID ticket_uid) {
        this.ticket_uid = ticket_uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

