package com.agh.bookstoreGatewaymaster.Accounts;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("accounts-service")
public interface AccountsProxy {
    @GetMapping("/account")
    public String test();
}
