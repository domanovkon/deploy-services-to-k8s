package com.domanov.gatewaycloud.dto;

public class FlightBuyRequest {

    private String flightNumber;

    private Integer price;

    private Boolean paidFromBalance;

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

    public Boolean getPaidFromBalance() {
        return paidFromBalance;
    }

    public void setPaidFromBalance(Boolean paidFromBalance) {
        this.paidFromBalance = paidFromBalance;
    }
}
