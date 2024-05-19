package com.heymart.supermarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/supermarket")
public class PublicSupermarketController {
    private final SupermarketService service;

    @Autowired
    public PublicSupermarketController() {
        this.service = service;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSupermarket(@PathVariable String id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> deleteSupermarket() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

}
