package com.agh.bookstoreProducts;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {
    @GetMapping("/product")
    String test() {
        return "to jest response z product service /product";
    }

}
