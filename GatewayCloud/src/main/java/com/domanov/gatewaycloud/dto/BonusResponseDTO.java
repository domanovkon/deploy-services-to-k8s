package com.domanov.gatewaycloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BonusResponseDTO {

    private int balance;

    private String status;

    @JsonProperty(value = "history")
    private List<BonusHistoryDTO> history;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty(value = "history")
    public List<BonusHistoryDTO> getHistory() {
        return history;
    }

    @JsonProperty(value = "history")
    public void setHistory(List<BonusHistoryDTO> history) {
        this.history = history;
    }

}
