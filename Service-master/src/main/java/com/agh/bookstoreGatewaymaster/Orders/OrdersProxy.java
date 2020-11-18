package com.agh.bookstoreGatewaymaster.Orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("orders-service")
public interface OrdersProxy {
    @GetMapping("/order")
    public String test();
}
