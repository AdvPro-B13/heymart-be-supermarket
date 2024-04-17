package com.heymart.supermarket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupermarketController {
    @GetMapping("/")
    public String home() {
        return "Hello from HeyMart Supermarket!";
    }
}
