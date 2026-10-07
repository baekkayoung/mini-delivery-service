package com.example.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MiniDeliveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniDeliveryServiceApplication.class, args);
    }

}
