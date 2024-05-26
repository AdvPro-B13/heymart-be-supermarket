package com.heymart.supermarket.controller;

import com.heymart.supermarket.model.Supermarket;
import com.heymart.supermarket.service.SupermarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supermarket")
public class PublicSupermarketController {
    private final SupermarketService service;

    @Autowired
    public PublicSupermarketController(SupermarketService supermarketService) {
        this.service = supermarketService;
    }

    @GetMapping("/get/{urlName}")
    public ResponseEntity<Object> getByUrlName(@PathVariable String urlName) {
        try {
            Supermarket supermarket = service.getSupermarket(urlName);
            return new ResponseEntity<>(supermarket, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Supermarket Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll() {
        List<Supermarket> supermarket = service.getAllSupermarket();
        return new ResponseEntity<>(supermarket, HttpStatus.OK);
    }
}
