package com.losagiles.CineAgile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.losagiles.CineAgile")
public class CineAgileApplication {
    public static void main(String[] args) {
        SpringApplication.run(CineAgileApplication.class, args);
    }
}