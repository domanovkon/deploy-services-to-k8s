package com.domanov.gatewaycloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class GatewayCloudApplication {

    public static void main(String[] args) {
//        SpringApplication.run(GatewayCloudApplication.class, args);
        SpringApplication app = new SpringApplication(GatewayCloudApplication.class);
        System.out.println(System.getenv("$PORT"));
        System.out.println(System.getenv("PORT"));
        app.setDefaultProperties(Collections
                .singletonMap("server.port", System.getenv("PORT")));//...
        app.run(args);
    }

}
