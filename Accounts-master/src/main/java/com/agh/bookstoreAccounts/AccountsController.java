package com.agh.bookstoreAccounts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {
    @GetMapping("/account")
    String test() {
        return "to jest response z accounts-service /account";
    }

    @PostMapping("/account")
    String test1() {
        return "to jest response z accounts-service /account POST";
    }
}
