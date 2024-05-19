package com.heymart.supermarket.service;

import com.heymart.supermarket.model.Supermarket
import org.springframework.stereotype.Service;

import java.util.List;

public interface SupermarketService {
    Supermarket create(Supermarket supermarket);
    Supermarket update(String id, Supermarket supermarket);
    Supermarket delete(String id);
    Supermarket get(String id);
    List<Supermarket> getAll();
    Supermarket getAdmin(String id);
    List<Supermarket> getAllAdmin();
}
