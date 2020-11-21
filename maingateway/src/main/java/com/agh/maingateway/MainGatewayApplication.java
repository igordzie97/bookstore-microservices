package com.agh.maingateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@EnableDiscoveryClient
@SpringBootApplication
@EnableZuulProxy
public class MainGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainGatewayApplication.class, args);
    }



}
