package com.agh.bookstoreService.bookstoreService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BookstoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreServiceApplication.class, args);
	}

}
