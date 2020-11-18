package com.agh.bookstoreGatewaymaster.Baskets;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("baskets-service")
public interface BasketsProxy {
    @GetMapping("/basket")
    public String test();
}
