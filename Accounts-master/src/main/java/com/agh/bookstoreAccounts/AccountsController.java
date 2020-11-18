package com.agh.bookstoreAccounts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {
    @GetMapping("/account")
    String test() {
        return "to jest response z accounts-service /account";
    }
}
