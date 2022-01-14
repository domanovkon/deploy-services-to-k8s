package com.domanov.ticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class TicketServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TicketServiceApplication.class);
        System.out.println(System.getenv("$PORT"));
        System.out.println(System.getenv("PORT"));
        app.setDefaultProperties(Collections
                .singletonMap("server.port", System.getenv("PORT")));//...
        app.run(args);
    }

}
