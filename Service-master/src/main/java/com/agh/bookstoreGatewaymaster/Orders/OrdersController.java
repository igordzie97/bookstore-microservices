package com.agh.bookstoreGatewaymaster.Orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {
    @Autowired
    private OrdersProxy proxy;

    @GetMapping("/proxy/order")
    public String getValue() {
        return proxy.test();
    }
}
