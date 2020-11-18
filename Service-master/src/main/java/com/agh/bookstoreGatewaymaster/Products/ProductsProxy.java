package com.agh.bookstoreGatewaymaster.Products;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("products-service")
public interface ProductsProxy {
    @GetMapping("/product")
    public String test();
}
