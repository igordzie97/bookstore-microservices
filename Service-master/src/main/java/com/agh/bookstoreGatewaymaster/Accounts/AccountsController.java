package com.agh.bookstoreGatewaymaster.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {
    @Autowired
    private AccountsProxy proxy;

    @GetMapping("/proxy/account")
    public String getValue() {
        return proxy.test();
    }
}
