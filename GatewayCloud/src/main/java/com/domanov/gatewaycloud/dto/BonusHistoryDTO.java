package com.domanov.gatewaycloud.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class BonusHistoryDTO {

    private LocalDateTime date;

    private UUID ticketUid;

    private int balanceDiff;

    private String operationType;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UUID getTicketUid() {
        return ticketUid;
    }

    public void setTicketUid(UUID ticketUid) {
        this.ticketUid = ticketUid;
    }

    public int getBalanceDiff() {
        return balanceDiff;
    }

    public void setBalanceDiff(int balanceDiff) {
        this.balanceDiff = balanceDiff;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
