package com.domanov.gatewaycloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserInformationDTO {

    @JsonProperty(value = "tickets")
    private List<TicketInformationDTO> ticketInformation;

    @JsonProperty(value = "privilege")
    private BuyTicketPrivilegeDTO privilege;

    public List<TicketInformationDTO> getTicketInformation() {
        return ticketInformation;
    }

    public void setTicketInformation(List<TicketInformationDTO> ticketInformation) {
        this.ticketInformation = ticketInformation;
    }

    public BuyTicketPrivilegeDTO getPrivilege() {
        return privilege;
    }

    public void setPrivilege(BuyTicketPrivilegeDTO privilege) {
        this.privilege = privilege;
    }
}
