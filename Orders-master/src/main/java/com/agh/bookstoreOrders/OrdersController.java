package com.agh.bookstoreOrders;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {


    @GetMapping("/order")
    String test() {
        return "to jest response z order service /order";
    }

}
