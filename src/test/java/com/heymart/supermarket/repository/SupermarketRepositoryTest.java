package com.heymart.supermarket.repository;

import com.heymart.supermarket.model.Supermarket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SupermarketRepositoryTest {

    @Autowired
    private SupermarketRepository supermarketRepository;
    @Autowired
    private TestEntityManager entityManager;
    private Supermarket dummySupermarket;

    @BeforeEach
    void setUp() {
        this.dummySupermarket = new Supermarket();
        this.dummySupermarket.setName("Dummy Mart");
        this.dummySupermarket.setColor("#525252");
        this.dummySupermarket.setUrlName("dummy mart");

        entityManager.persist(dummySupermarket);
    }

    @Test
    void saveSupermarket() {
        Supermarket supermarket = new Supermarket();
        supermarket.setName("Fresh Produce Mart");
        supermarket.setColor("#34e324");
        supermarket.setUrlName("fresh-produce-mart");

        Supermarket savedSupermarket = supermarketRepository.save(supermarket);
        assertEquals(entityManager.find(Supermarket.class, savedSupermarket.getId()), supermarket);
    }

    @Test
    void saveSupermarket_UsedUrl() {
        Supermarket supermarket = new Supermarket();
        supermarket.setName("Dummy Mart 2");
        supermarket.setColor("#34e324");
        supermarket.setUrlName("dummy-mart");

        assertThrows(DataIntegrityViolationException.class, () -> supermarketRepository.save(supermarket));
    }

    @Test
    void saveSupermarket_EmptyFields() {
        Supermarket supermarket = new Supermarket();
        assertThrows(DataIntegrityViolationException.class, () -> supermarketRepository.save(supermarket));
    }

    @Test
    void updateSupermarket() {
        String newName = "Hey Hey!!";
        String newColorCode = "#2d2d2d";
        String newUrlName = "hey-hey";
        dummySupermarket.setName(newName);
        dummySupermarket.setColor(newColorCode);
        dummySupermarket.setUrlName(newUrlName);

        supermarketRepository.save(dummySupermarket);
        assertEquals(entityManager.find(Supermarket.class, dummySupermarket.getId()).getName(), newName);
        assertEquals(entityManager.find(Supermarket.class, dummySupermarket.getId()).getColor(), newColorCode);
        assertEquals(entityManager.find(Supermarket.class, dummySupermarket.getId()).getUrlName(), newUrlName);
    }

    @Test
    void deleteSupermarket() {
        supermarketRepository.delete(dummySupermarket);
        assertNull(entityManager.find(Supermarket.class, dummySupermarket.getId()));
    }

    @Test
    void findSupermarketById_Success() {
        Optional<Supermarket> foundSupermarket = supermarketRepository.findById(dummySupermarket.getId());
        assertTrue(foundSupermarket.isPresent());
        assertEquals(foundSupermarket.get(), dummySupermarket);
    }

    @Test
    void findSupermarketById_Fail() {
        Optional<Supermarket> foundSupermarket = supermarketRepository.findById(Long.MAX_VALUE);
        assertFalse(foundSupermarket.isPresent());
    }

    @Test
    void findSupermarketByUrlName_Success() {
        Optional<Supermarket> foundSupermarket = supermarketRepository.findByUrlName("dummy-mart");
        assertTrue(foundSupermarket.isPresent());
        assertEquals(foundSupermarket.get(), dummySupermarket);
    }

    @Test
    void findSupermarketByUrlName_Fail() {
        Optional<Supermarket> foundSupermarket = supermarketRepository.findByUrlName("hey-mart");
        assertFalse(foundSupermarket.isPresent());
    }
}