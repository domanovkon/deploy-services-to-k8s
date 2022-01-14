package com.domanov.gatewaycloud.service;

import com.domanov.gatewaycloud.dto.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service("GatewayService")
public class GatewayService {

    private static final String GET_BONUS_ACCOUNT_PRIVILEGES = "http://10.88.4.138:8082/api/v1/privilege";
    private static final String GET_FLIGHTS = "http://10.88.12.220:8081/api/v1/flights";
    private static final String BUY_TICKETS = "http://10.88.5.29:8083/api/v1/tickets";

//    private static final String GET_BONUS_ACCOUNT_PRIVILEGES = "http://localhost:8082/api/v1/privilege";
//    private static final String GET_FLIGHTS = "http://localhost:8081/api/v1/flights";
//    private static final String BUY_TICKETS = "http://localhost:8083/api/v1/tickets";

//    private static final String GET_BONUS_ACCOUNT_PRIVILEGES = "http://192.168.50.240:8082/api/v1/privilege";
//    private static final String GET_FLIGHTS = "http://192.168.50.240:8081/api/v1/flights";
//    private static final String BUY_TICKETS = "http://192.168.50.240:8083/api/v1/tickets";

//    private static final String GET_BONUS_ACCOUNT_PRIVILEGES = "https://lab2-bonus-service.herokuapp.com/api/v1/privilege";
//    private static final String GET_FLIGHTS = "https://lab2-flight-service.herokuapp.com/api/v1/flights";
//    private static final String BUY_TICKETS = "https://lab3-ticket-service.herokuapp.com/api/v1/tickets";


    public BonusResponseDTO getBonusAccountPrivileges(String username) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .header("X-User-Name", username)
                .uri(URI.create(GET_BONUS_ACCOUNT_PRIVILEGES))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        JsonNode jsonNode = objectMapper.readTree(response.body());

        int balance = jsonNode.get("balance").asInt();
        String status = jsonNode.get("status").asText();
        List<BonusHistoryDTO> bonusHistoryDTOS = objectMapper.readValue(jsonNode.get("history").toPrettyString(), new TypeReference<List<BonusHistoryDTO>>() {
        });

        BonusResponseDTO bonusResponseDTO = new BonusResponseDTO();
        bonusResponseDTO.setBalance(balance);
        bonusResponseDTO.setStatus(status);
        bonusResponseDTO.setHistory(bonusHistoryDTOS);
        return bonusResponseDTO;
    }


    public FlightPageResponseDTO findAll(int page, int size) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String pageUriString = UriComponentsBuilder.fromUriString(GET_FLIGHTS).queryParam("page", page).toUriString();
        String sizeUriString = UriComponentsBuilder.fromUriString(pageUriString).queryParam("size", size).toUriString();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(sizeUriString))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        JsonNode jsonNode = objectMapper.readTree(response.body());
        int pageResp = jsonNode.get("page").asInt();
        int pageSize = jsonNode.get("pageSize").asInt();
        long totalElements = jsonNode.get("totalElements").asLong();
        List<FlightResponseDTO> flightResponseDTOS = objectMapper.readValue(jsonNode.get("items").toPrettyString(), new TypeReference<List<FlightResponseDTO>>() {
        });

        FlightPageResponseDTO flightPageResponseDTO = new FlightPageResponseDTO();
        flightPageResponseDTO.setPage(pageResp);
        flightPageResponseDTO.setPageSize(pageSize);
        flightPageResponseDTO.setTotalElements(totalElements);
        flightPageResponseDTO.setFlightDTO(flightResponseDTOS);
        return flightPageResponseDTO;
    }

    public FlightBuyResponseDTO buyTicket(String username, FlightBuyRequest flightBuyRequest) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(flightBuyRequest);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("X-User-Name", username)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(BUY_TICKETS))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper1 = new ObjectMapper();
        objectMapper1.findAndRegisterModules();
        JsonNode jsonNode = objectMapper1.readTree(response.body());

        UUID ticketUid = UUID.fromString(jsonNode.get("ticket_uid").asText());
        String flightNumber = jsonNode.get("flightNumber").asText();
        Integer price = jsonNode.get("price").asInt();
        String status = jsonNode.get("status").asText();

        FlightBuyResponseDTO flightBuyResponseDTO = new FlightBuyResponseDTO();
        flightBuyResponseDTO.setTicketUid(ticketUid);
        flightBuyResponseDTO.setFlightNumber(flightNumber);
        flightBuyResponseDTO.setPrice(price);
        flightBuyResponseDTO.setStatus(status);

        HttpClient httpClientGetFlight = HttpClient.newHttpClient();
        String uriStringGetFlight = UriComponentsBuilder.fromUriString(GET_FLIGHTS + "/" + flightNumber).toUriString();
        HttpRequest httpRequestGetFlight = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uriStringGetFlight))
                .build();
        HttpResponse<String> responseGetFlight = httpClientGetFlight.send(httpRequestGetFlight, HttpResponse.BodyHandlers.ofString());

        jsonNode = objectMapper.readTree(responseGetFlight.body());
        String fromAirport = jsonNode.get("fromAirport").asText();
        String toAirport = jsonNode.get("toAirport").asText();
        String dateString = jsonNode.get("date").asText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.parse(dateString, formatter);
        flightBuyResponseDTO.setToAirport(toAirport);
        flightBuyResponseDTO.setFromAirport(fromAirport);
        flightBuyResponseDTO.setDate(date);

        int paidByBonuses = 0;
        int paidByMoney = price;
        String operationType = "FILL_IN_BALANCE";

        BonusResponseDTO bonusResponseDTO = getBonusAccountPrivileges(username);
        if (flightBuyRequest.getPaidFromBalance().equals(true)) {
            paidByBonuses = bonusResponseDTO.getBalance();
            paidByMoney = price - bonusResponseDTO.getBalance();
            operationType = "DEBIT_THE_ACCOUNT";
        }

        UpdateBonusesRequestDTO updateBonusesRequestDTO = new UpdateBonusesRequestDTO();
        updateBonusesRequestDTO.setTicketUid(ticketUid);
        updateBonusesRequestDTO.setPaidFromBalance(flightBuyRequest.getPaidFromBalance());
        updateBonusesRequestDTO.setPaidByBonuses(paidByBonuses);
        updateBonusesRequestDTO.setPaidByMoney(paidByMoney);
        updateBonusesRequestDTO.setOperationType(operationType);
        updateBonusesRequestDTO.setDate(date);

        String requestBodyChangeBonuse = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(updateBonusesRequestDTO);

        HttpClient httpClientChangeBonuses = HttpClient.newHttpClient();
        HttpRequest httpRequestChangeBonuses = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("X-User-Name", username)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBodyChangeBonuse))
                .uri(URI.create(GET_BONUS_ACCOUNT_PRIVILEGES))
                .build();
        HttpResponse<String> responseChangeBonuses = httpClientChangeBonuses.send(httpRequestChangeBonuses, HttpResponse.BodyHandlers.ofString());

        jsonNode = objectMapper.readTree(responseChangeBonuses.body());
        int bonusBalance = jsonNode.get("balance").asInt();
        String bonusStatus = jsonNode.get("status").asText();

        flightBuyResponseDTO.setPaidByMoney(paidByMoney);
        flightBuyResponseDTO.setPaidByBonuses(paidByBonuses);
        BuyTicketPrivilegeDTO buyTicketPrivilegeDTO = new BuyTicketPrivilegeDTO();
        buyTicketPrivilegeDTO.setBalance(bonusBalance);
        buyTicketPrivilegeDTO.setStatus(bonusStatus);
        flightBuyResponseDTO.setPrivilege(buyTicketPrivilegeDTO);

        return flightBuyResponseDTO;
    }

    public TicketInformationDTO getTicket(UUID ticketUid, String username) throws IOException, InterruptedException {
        String uriStringGetTicket = UriComponentsBuilder.fromUriString(BUY_TICKETS + "/" + ticketUid).toUriString();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .header("Content-Type", "application/json")
                .header("X-User-Name", username)
                .uri(URI.create(uriStringGetTicket))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        JsonNode jsonNode = objectMapper.readTree(response.body());
        UUID ticketUidRes = UUID.fromString(jsonNode.get("ticketUid").asText());
        String flightNumber = jsonNode.get("flightNumber").asText();
        Integer price = jsonNode.get("price").asInt();
        String status = jsonNode.get("status").asText();

        TicketInformationDTO ticketInformationDTO = new TicketInformationDTO();
        ticketInformationDTO.setTicketUid(ticketUidRes);
        ticketInformationDTO.setFlightNumber(flightNumber);
        ticketInformationDTO.setPrice(price);
        ticketInformationDTO.setStatus(status);

        HttpClient httpClientGetFlight = HttpClient.newHttpClient();
        String uriStringGetFlight = UriComponentsBuilder.fromUriString(GET_FLIGHTS + "/" + flightNumber).toUriString();
        HttpRequest httpRequestGetFlight = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uriStringGetFlight))
                .build();
        HttpResponse<String> responseGetFlight = httpClientGetFlight.send(httpRequestGetFlight, HttpResponse.BodyHandlers.ofString());

        jsonNode = objectMapper.readTree(responseGetFlight.body());
        String fromAirport = jsonNode.get("fromAirport").asText();
        String toAirport = jsonNode.get("toAirport").asText();
        String dateString = jsonNode.get("date").asText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.parse(dateString, formatter);
        ticketInformationDTO.setFromAirport(fromAirport);
        ticketInformationDTO.setToAirport(toAirport);
        ticketInformationDTO.setDate(date);

        return ticketInformationDTO;
    }

    public List<TicketInformationDTO> getTickets(String username) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .header("X-User-Name", username)
                .uri(URI.create(BUY_TICKETS))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        JsonNode jsonNode = objectMapper.readTree(response.body());

        List<TicketInformationDTO> ticketInformationDTOS = objectMapper.readValue(response.body(), new TypeReference<List<TicketInformationDTO>>() {
        });

        for (TicketInformationDTO ticketInformationDTO : ticketInformationDTOS) {
            HttpClient httpClientGetFlight = HttpClient.newHttpClient();
            String uriStringGetFlight = UriComponentsBuilder.fromUriString(GET_FLIGHTS + "/" + ticketInformationDTO.getFlightNumber()).toUriString();
            HttpRequest httpRequestGetFlight = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(uriStringGetFlight))
                    .build();
            HttpResponse<String> responseGetFlight = httpClientGetFlight.send(httpRequestGetFlight, HttpResponse.BodyHandlers.ofString());

            jsonNode = objectMapper.readTree(responseGetFlight.body());
            String fromAirport = jsonNode.get("fromAirport").asText();
            String toAirport = jsonNode.get("toAirport").asText();
            String dateString = jsonNode.get("date").asText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime date = LocalDateTime.parse(dateString, formatter);
            ticketInformationDTO.setFromAirport(fromAirport);
            ticketInformationDTO.setToAirport(toAirport);
            ticketInformationDTO.setDate(date);
        }
        return ticketInformationDTOS;
    }

    public UserInformationDTO getUser(String username) throws IOException, InterruptedException {
        List<TicketInformationDTO> tickets = getTickets(username);
        UserInformationDTO userInformationDTO = new UserInformationDTO();
        userInformationDTO.setTicketInformation(tickets);

        BonusResponseDTO bonusResponseDTO = new BonusResponseDTO();
        bonusResponseDTO = getBonusAccountPrivileges(username);
        BuyTicketPrivilegeDTO buyTicketPrivilegeDTO = new BuyTicketPrivilegeDTO();
        buyTicketPrivilegeDTO.setStatus(bonusResponseDTO.getStatus());
        buyTicketPrivilegeDTO.setBalance(bonusResponseDTO.getBalance());
        userInformationDTO.setPrivilege(buyTicketPrivilegeDTO);
        return userInformationDTO;
    }

    public ResponseEntity<TicketInformationDTO> ticketCancel(UUID ticketUid, String username) throws IOException, InterruptedException {
        String uriStringGetTicket = UriComponentsBuilder.fromUriString(BUY_TICKETS + "/" + ticketUid).toUriString();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .DELETE()
                .header("Content-Type", "application/json")
                .header("X-User-Name", username)
                .uri(URI.create(uriStringGetTicket))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            BonusResponseDTO bonusResponseDTO = getBonusAccountPrivileges(username);

            List<BonusHistoryDTO> historyList = bonusResponseDTO.getHistory();
            for (BonusHistoryDTO bh : historyList) {
                if (bh.getTicketUid().equals(ticketUid)) {
                    if (bh.getOperationType().equals("FILL_IN_BALANCE")) {
                        BonusReturnDTO bonusReturnDTO = new BonusReturnDTO();
                        bonusReturnDTO.setTicketUid(bh.getTicketUid());
                        bonusReturnDTO.setBalance(bonusResponseDTO.getBalance());
                        bonusReturnDTO.setOperationType(bh.getOperationType());
                        bonusReturnDTO.setBalanceDiff(bh.getBalanceDiff());

                        ObjectMapper objectMapper = new ObjectMapper();
                        String requestBodyBonusReturn = objectMapper
                                .writerWithDefaultPrettyPrinter()
                                .writeValueAsString(bonusReturnDTO);

                        HttpClient httpClientBonusReturn = HttpClient.newHttpClient();
                        HttpRequest httpRequestBonusReturn = HttpRequest.newBuilder()
                                .header("Content-Type", "application/json")
                                .header("X-User-Name", username)
                                .PUT(HttpRequest.BodyPublishers.ofString(requestBodyBonusReturn))
                                .uri(URI.create(GET_BONUS_ACCOUNT_PRIVILEGES+"/return"))
                                .build();
                        HttpResponse<String> BonusReturn = httpClientBonusReturn.send(httpRequestBonusReturn, HttpResponse.BodyHandlers.ofString());

                    }
                }
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
