package com.heymart.supermarket.controller;

import com.heymart.supermarket.model.Supermarket;
import com.heymart.supermarket.service.SupermarketService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpRequest;

@Controller
@RequestMapping("/admin/supermarket")
public class AdminSupermarketController {
    private final SupermarketService service;

    @Autowired
    public AdminSupermarketController() {
        this.service = service;
    }

    @GetMapping("/create")
    public ResponseEntity<?> createSupermarket(@RequestBody Supermarket supermarket) {
        return new ResponseEntity<>(service.create(supermarket), HttpStatus.CREATED);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<?> updateSupermarket(@PathVariable String id, @RequestBody Supermarket supermarket) {
        return new ResponseEntity<>(service.update(id, supermarket), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSupermarket(@PathVariable String id) {
        return new ResponseEntity<>(service.getAdmin(id), HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteSupermarket(@PathVariable String id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> deleteSupermarket() {
        return new ResponseEntity<>(service.getAllAdmin(), HttpStatus.OK);
    }

}
