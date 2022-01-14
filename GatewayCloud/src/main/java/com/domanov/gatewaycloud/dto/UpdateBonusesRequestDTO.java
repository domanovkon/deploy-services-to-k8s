package com.domanov.gatewaycloud.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateBonusesRequestDTO {

    private UUID ticketUid;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;

    private Boolean paidFromBalance;

    private Integer paidByBonuses;

    private Integer paidByMoney;

    private String operationType;

    public UUID getTicketUid() {
        return ticketUid;
    }

    public void setTicketUid(UUID ticketUid) {
        this.ticketUid = ticketUid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getPaidFromBalance() {
        return paidFromBalance;
    }

    public void setPaidFromBalance(Boolean paidFromBalance) {
        this.paidFromBalance = paidFromBalance;
    }

    public Integer getPaidByBonuses() {
        return paidByBonuses;
    }

    public void setPaidByBonuses(Integer paidByBonuses) {
        this.paidByBonuses = paidByBonuses;
    }

    public Integer getPaidByMoney() {
        return paidByMoney;
    }

    public void setPaidByMoney(Integer paidByMoney) {
        this.paidByMoney = paidByMoney;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
