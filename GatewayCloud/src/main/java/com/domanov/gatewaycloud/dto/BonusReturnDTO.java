package com.domanov.gatewaycloud.dto;

import java.util.UUID;

public class BonusReturnDTO {

    private UUID ticketUid;

    private String operationType;

    private int balanceDiff;

    private int balance;

    public UUID getTicketUid() {
        return ticketUid;
    }

    public void setTicketUid(UUID ticketUid) {
        this.ticketUid = ticketUid;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public int getBalanceDiff() {
        return balanceDiff;
    }

    public void setBalanceDiff(int balanceDiff) {
        this.balanceDiff = balanceDiff;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
