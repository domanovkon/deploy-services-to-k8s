package com.domanov.flightservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FlightPageDTO {
    private int page;

    private int pageSize;

    private long totalElements;

    @JsonProperty(value = "items")
    private List<FlightDTO> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    @JsonProperty(value = "items")
    public List<FlightDTO> getFlightDTO() {
        return items;
    }

    @JsonProperty(value = "items")
    public void setFlightDTO(List<FlightDTO> flightDTO) {
        this.items = flightDTO;
    }
}
