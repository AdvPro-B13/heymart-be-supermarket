package com.heymart.supermarket.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

public class SupermarketTest {
    private Supermarket supermarket;
    private Set<String> managerIds;

    @BeforeEach
    void setUp() {
        this.managerIds = new HashSet<>();
        this.managerIds.add("7e09a902-9116-415f-88b7-53fea59c2d4d");
        this.managerIds.add("65569df3-2e29-4778-95ec-d8a0fd802ddd");

        this.supermarket = new Supermarket();
        this.supermarket.setId("c3ba2457-2762-4d52-899e-2d0181bef8f3");
        this.supermarket.setName("Fresh Produce Mart");
        this.supermarket.setColor("#34e324");
        this.supermarket.setUrlName("fresh-produce-mart");
        this.supermarket.setManagers(this.managerIds);
    }

    @Test
    void testGetId() {
        assertEquals("c3ba2457-2762-4d52-899e-2d0181bef8f3", supermarket.getId());
    }

    @Test
    void testGetName() {
        assertEquals("Fresh Produce Mart", supermarket.getName());
    }

    @Test
    void testGetColor() {
        assertEquals("#34e324", supermarket.getColor());
    }

    @Test
    void testGetUrlName() {
        assertEquals("fresh-produce-mart", supermarket.getUrlName());
    }

    @Test
    void testGetManagers() {
        for (String managerId : managerIds) {
            assertTrue(supermarket.getManagers().contains(managerId));
        }
    }

    @Test
    void testSetEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> supermarket.setName(""));
    }

    @Test
    void testSetEmptyUrlName() {
        assertThrows(IllegalArgumentException.class, () -> supermarket.setUrlName(""));
    }
}
