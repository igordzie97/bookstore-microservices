package com.agh.bookstoreGatewaymaster.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {
    @Autowired
    private ProductsProxy proxy;

    @GetMapping("/proxy/product")
    public String getValue() {
        return proxy.test();
    }
}
