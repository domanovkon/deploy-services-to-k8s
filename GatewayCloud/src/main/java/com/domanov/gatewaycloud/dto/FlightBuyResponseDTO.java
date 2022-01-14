package com.domanov.gatewaycloud.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class FlightBuyResponseDTO {

    private UUID ticketUid;

    private String flightNumber;

    private String fromAirport;

    private String toAirport;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    private Integer price;

    private Integer paidByMoney;

    private Integer paidByBonuses;

    private String status;

    @JsonProperty(value = "privilege")
    private BuyTicketPrivilegeDTO privilege;

    public UUID getTicketUid() {
        return ticketUid;
    }

    public void setTicketUid(UUID ticketUid) {
        this.ticketUid = ticketUid;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPaidByMoney() {
        return paidByMoney;
    }

    public void setPaidByMoney(Integer paidByMoney) {
        this.paidByMoney = paidByMoney;
    }

    public Integer getPaidByBonuses() {
        return paidByBonuses;
    }

    public void setPaidByBonuses(Integer paidByBonuses) {
        this.paidByBonuses = paidByBonuses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BuyTicketPrivilegeDTO getPrivilege() {
        return privilege;
    }

    public void setPrivilege(BuyTicketPrivilegeDTO privilege) {
        this.privilege = privilege;
    }
}
