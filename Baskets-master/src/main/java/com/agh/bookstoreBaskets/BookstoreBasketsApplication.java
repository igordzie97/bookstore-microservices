package com.agh.bookstoreBaskets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BookstoreBasketsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreBasketsApplication.class, args);
    }

}
