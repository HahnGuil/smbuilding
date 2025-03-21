package com.manager.smbuilding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SmBuildingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmBuildingApplication.class, args);
    }

}
