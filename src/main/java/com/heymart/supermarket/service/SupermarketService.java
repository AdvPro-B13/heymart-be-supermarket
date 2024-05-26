package com.heymart.supermarket.service;

import com.heymart.supermarket.model.Supermarket;
import com.heymart.supermarket.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupermarketService {
    private SupermarketRepository supermarketRepository;

    @Autowired
    public void setSupermarketRepository(SupermarketRepository supermarketRepository) {
        this.supermarketRepository = supermarketRepository;
    }

    public Supermarket create(Supermarket supermarket) {
        return supermarketRepository.save(supermarket);
    }

    public Supermarket update(Long id, Supermarket supermarket) throws Exception {
        Optional<Supermarket> foundSupermarket = supermarketRepository.findById(id);
        if (foundSupermarket.isPresent()) {
            return supermarketRepository.save(supermarket);
        }
        throw new Exception("Cannot find Supermarket with matching ID to update");
    }

    public void delete(Long id) throws Exception {
        Optional<Supermarket> foundSupermarket = supermarketRepository.findById(id);
        if (foundSupermarket.isPresent()) {
            supermarketRepository.deleteById(id);
            return;
        }
        throw new Exception("Cannot find Supermarket with matching ID to delete");
    }

    public Supermarket getSupermarketFull(Long id) throws Exception {
        Optional<Supermarket> supermarket = supermarketRepository.findById(id);
        if (supermarket.isPresent()) {
            return supermarket.get();
        }
        throw new Exception("Cannot find Supermarket with matching urlName");
    }

    public List<Supermarket> getAllSupermarketFull() {
        return supermarketRepository.findAll();
    }

    public Supermarket getSupermarket(String urlName) throws Exception {
        Optional<Supermarket> supermarket = supermarketRepository.findByUrlName(urlName);
        if (supermarket.isPresent()) {
            Supermarket foundSupermarket = supermarket.get();
            foundSupermarket.setManagerIds(null);
            return foundSupermarket;
        }
        throw new Exception("Cannot find Supermarket with matching urlName");
    }

    public List<Supermarket> getAllSupermarket() {
        List<Supermarket> supermarkets = supermarketRepository.findAll();
        if (!supermarkets.isEmpty()) {
            for (Supermarket supermarket : supermarkets) {
                supermarket.setManagerIds(null);
            }
        }
        return supermarkets;
    }
}
