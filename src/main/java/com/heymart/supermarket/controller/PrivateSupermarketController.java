package com.heymart.supermarket.controller;

import com.heymart.supermarket.enums.SupermarketActions;
import com.heymart.supermarket.model.Supermarket;
import com.heymart.supermarket.service.AuthServiceClient;
import com.heymart.supermarket.service.SupermarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/supermarket")
public class PrivateSupermarketController {
    private final SupermarketService service;
    private final AuthServiceClient authServiceClient;

    @Autowired
    public PrivateSupermarketController(SupermarketService supermarketService, AuthServiceClient authServiceClient) {
        this.service = supermarketService;this.authServiceClient = authServiceClient;


    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Supermarket supermarket, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (!authServiceClient.verifyUserAuthorization(SupermarketActions.AUTH_CREATE_MANAGER.getValue(), authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

            try {
            Supermarket newSupermarket = service.create(supermarket);
            return new ResponseEntity<>(newSupermarket, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot create Supermarket", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> create(@PathVariable Long id, @RequestBody Supermarket supermarket, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (!authServiceClient.verifyUserAuthorization(SupermarketActions.AUTH_CREATE_MANAGER.getValue(), authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            Supermarket updated = service.update(id, supermarket);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot update Supermarket", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (!authServiceClient.verifyUserAuthorization(SupermarketActions.AUTH_CREATE_MANAGER.getValue(), authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            service.delete(id);
            return new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot create Supermarket", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (!authServiceClient.verifyUserAuthorization(SupermarketActions.AUTH_CREATE_MANAGER.getValue(), authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            Supermarket supermarket = service.getSupermarketFull(id);
            return new ResponseEntity<>(supermarket, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Supermarket Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (!authServiceClient.verifyUserAuthorization(SupermarketActions.AUTH_CREATE_MANAGER.getValue(), authorizationHeader)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Supermarket> supermarket = service.getAllSupermarketFull();
        return new ResponseEntity<>(supermarket, HttpStatus.OK);
    }
}
