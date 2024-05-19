package com.heymart.supermarket.service;

import com.heymart.supermarket.model.Supermarket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupermarketServiceImpl implements SupermarketService {
    @Autowired
    private SupermarketRepository repository;

    public Supermarket create(Supermarket supermarket) {
        return null;
    }
    public Supermarket update(String id, Supermarket supermarket) {
        return null;
    }
    public Supermarket delete(String id) {
        return null;
    }
    public Supermarket get(String id) {
        return null;
    }
    public List<Supermarket> getAll() {
        return null;
    }
    public Supermarket getAdmin(String id) {
        return null;
    }
    public List<Supermarket> getAllAdmin() {
        return null;
    }
}
