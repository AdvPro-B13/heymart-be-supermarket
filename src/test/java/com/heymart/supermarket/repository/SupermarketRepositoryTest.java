package com.heymart.supermarket.repository;

import com.heymart.supermarket.model.Supermarket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SupermarketRepositoryTest {

    @Autowired
    private SupermarketRepository supermarketRepository;
    @Autowired
    private TestEntityManager entityManager;
    private Supermarket dummySupermarket;
    private Set<String> dummyManagerIds;

    @BeforeEach
    void setUp() {
        this.dummyManagerIds = new HashSet<>();
        this.dummyManagerIds.addAll(List.of(
                String.valueOf(Long.MAX_VALUE),
                String.valueOf(Long.MAX_VALUE-1),
                String.valueOf(Long.MAX_VALUE-2),
                String.valueOf(Long.MAX_VALUE-3)
        ));

        this.dummySupermarket = new Supermarket();
        this.dummySupermarket.setName("Dummy Mart");
        this.dummySupermarket.setColor("#525252");
        this.dummySupermarket.setUrlName("dummy mart");
        this.dummySupermarket.setManagerIds(dummyManagerIds);

        entityManager.persist(dummySupermarket);
    }

    @Test
    void saveSupermarket() {
        Supermarket supermarket = new Supermarket();
        supermarket.setName("Fresh Produce Mart");
        supermarket.setColor("#34e324");
        supermarket.setUrlName("fresh-produce-mart");
        supermarket.setManagerIds(dummyManagerIds);

        Supermarket savedSupermarket = supermarketRepository.save(supermarket);
        assertEquals(entityManager.find(Supermarket.class, savedSupermarket.getId()), supermarket);
    }

    @Test
    void saveSupermarket_UsedUrl() {
        Supermarket supermarket = new Supermarket();
        supermarket.setName("Dummy Mart 2");
        supermarket.setColor("#34e324");
        supermarket.setUrlName("dummy-mart");
        supermarket.setManagerIds(dummyManagerIds);

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

    // Managers
    @Test
    void addManagerIdToSupermarket() {
        dummyManagerIds.add("e3f04d7b-d7b4-47cd-8aa4-14d101e7124f");
        dummySupermarket.addManagerId("e3f04d7b-d7b4-47cd-8aa4-14d101e7124f");

        Supermarket savedSupermarket = supermarketRepository.save(dummySupermarket);
        assertEquals(entityManager.find(Supermarket.class, savedSupermarket.getId()).getManagerIds(), dummyManagerIds);
    }

    @Test
    void removeManagerIdFromSupermarket() {
        dummyManagerIds.remove(String.valueOf(Long.MAX_VALUE-3));
        dummySupermarket.removeManagerId(String.valueOf(Long.MAX_VALUE-3));

        Supermarket savedSupermarket = supermarketRepository.save(dummySupermarket);
        assertEquals(entityManager.find(Supermarket.class, savedSupermarket.getId()).getManagerIds(), dummyManagerIds);

    }
}