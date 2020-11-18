package com.agh.bookstoreGatewaymaster.Baskets;

import com.agh.bookstoreGatewaymaster.Products.ProductsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasketsController {
    @Autowired
    private BasketsProxy proxy;

    @GetMapping("/proxy/basket")
    public String getValue() {
        return proxy.test();
    }
}
