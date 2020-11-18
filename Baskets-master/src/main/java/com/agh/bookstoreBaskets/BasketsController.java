package com.agh.bookstoreBaskets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasketsController {
    @GetMapping("/basket")
    String test() {
        return "to jest response z baskets-service /basket";
    }
}
