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

        Supermarket.SupermarketBuilder builder = new Supermarket.SupermarketBuilder();
        builder.setName("Dummy Mart");
        builder.setColor("#525252");
        builder.setUrlName("dummy mart");
        builder.setManagerIds(dummyManagerIds);

        this.dummySupermarket = builder.build();
        entityManager.persist(dummySupermarket);
    }

    @Test
    void saveSupermarket() {
        Supermarket supermarket = new Supermarket.SupermarketBuilder(
                "Fresh Produce Mart",
                "#34e324",
                "fresh-produce-mart",
                dummyManagerIds
        ).build();

        Supermarket savedSupermarket = supermarketRepository.save(supermarket);
        assertEquals(entityManager.find(Supermarket.class, savedSupermarket.getId()), supermarket);
    }

    @Test
    void saveSupermarket_UsedUrl() {
        Supermarket supermarket = new Supermarket.SupermarketBuilder(
                "Dummy Mart 2",
                "#34e324",
                "dummy-mart",
                dummyManagerIds
        ).build();

        assertThrows(DataIntegrityViolationException.class, () -> supermarketRepository.save(supermarket));
    }

    @Test
    void saveSupermarket_EmptyFields() {
        Supermarket supermarket = new Supermarket();
        assertThrows(DataIntegrityViolationException.class, () -> supermarketRepository.save(supermarket));
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
}