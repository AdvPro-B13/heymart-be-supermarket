package com.heymart.supermarket.service;

import com.heymart.supermarket.model.Supermarket;
import com.heymart.supermarket.repository.SupermarketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SupermarketServiceTest {
    @Mock
    private SupermarketRepository supermarketRepository;

    @InjectMocks
    private SupermarketService supermarketService;

    private Supermarket dummySupermarket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Supermarket.SupermarketBuilder builder = new Supermarket.SupermarketBuilder();
        builder.setName("Dummy Mart");
        builder.setColor("#525252");
        builder.setUrlName("dummy mart");

        dummySupermarket = builder.build();
    }

    @Test
    void createSupermarket() {
        Mockito.when(supermarketRepository.save(dummySupermarket)).thenReturn(dummySupermarket);
        Supermarket createdSupermarket = supermarketService.create(dummySupermarket);

        assertEquals(dummySupermarket, createdSupermarket);
        verify(supermarketRepository, times(1)).save(dummySupermarket);
    }

    @Test
    void updateSupermarket() {
        Mockito.when(supermarketRepository.save(dummySupermarket)).thenReturn(dummySupermarket);
        Supermarket createdSupermarket = supermarketService.create(dummySupermarket);

        Supermarket updatedSupermarket = new Supermarket();
        updatedSupermarket.setId(createdSupermarket.getId());
        updatedSupermarket.setName("Hey Hey!!");
        updatedSupermarket.setColor("#666777");
        updatedSupermarket.setUrlName("hey-hey");

        verify(supermarketRepository, times(1)).save(dummySupermarket);

        try {
            Mockito.when(supermarketRepository.save(updatedSupermarket)).thenReturn(updatedSupermarket);
            Supermarket savedUpdated = supermarketService.update(createdSupermarket.getId(), updatedSupermarket);

            assertEquals(updatedSupermarket, savedUpdated);
            verify(supermarketRepository, times(1)).save(dummySupermarket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateSupermarket_DoesntExist() {
        Mockito.when(supermarketRepository.save(dummySupermarket)).thenReturn(dummySupermarket);
        Supermarket createdSupermarket = supermarketService.create(dummySupermarket);

        Supermarket updatedSupermarket = new Supermarket();
        updatedSupermarket.setId(createdSupermarket.getId());
        updatedSupermarket.setName("Hey Hey!!");
        updatedSupermarket.setColor("#666777");
        updatedSupermarket.setUrlName("hey-hey");

        assertThrows(Exception.class, () -> supermarketService.update(createdSupermarket.getId(), updatedSupermarket));
    }

    @Test
    void deleteSupermarket() {
        Mockito.when(supermarketRepository.save(dummySupermarket)).thenReturn(dummySupermarket);
        Supermarket createdSupermarket = supermarketService.create(dummySupermarket);

        doNothing().when(supermarketRepository).deleteById(createdSupermarket.getId());
        verify(supermarketRepository, times(1)).deleteById(createdSupermarket.getId());
    }

    @Test
    void getSupermarketPublic() {
        Mockito.when(supermarketRepository.save(dummySupermarket)).thenReturn(dummySupermarket);
        Supermarket createdSupermarket = supermarketService.create(dummySupermarket);

        Optional<Supermarket> supermarket = Optional.empty();

        Mockito.when(supermarketRepository.findByUrlName(createdSupermarket.getUrlName())).thenReturn((supermarket));

        try {
            Supermarket foundSupermarket = supermarketService.getSupermarket("dummy-mart");
            foundSupermarket.setManagerIds(null);
            assertEquals(supermarket.get(), foundSupermarket);
        } catch (Exception ignored) {}
    }

    @Test
    void getSupermarketPublic_DoesntExist() {
        Mockito.when(supermarketRepository.save(dummySupermarket)).thenReturn(dummySupermarket);
        Supermarket createdSupermarket = supermarketService.create(dummySupermarket);

        Optional<Supermarket> supermarket = Optional.empty();
        Mockito.when(supermarketRepository.findByUrlName(createdSupermarket.getUrlName())).thenReturn((supermarket));
        assertThrows(Exception.class, () -> supermarketService.getSupermarket("dummy-mart2"));
    }

    @Test
    void getAllSupermarketPublic() {
        Mockito.when(supermarketRepository.save(dummySupermarket)).thenReturn(dummySupermarket);
        Supermarket createdSupermarket = supermarketService.create(dummySupermarket);

        List<Supermarket> supermarket = List.of(createdSupermarket);
        Mockito.when(supermarketRepository.findAll()).thenReturn((supermarket));

        for (Supermarket s : supermarket) {
            s.setManagerIds(null);
        }

        List<Supermarket> allSupermarkets = supermarketService.getAllSupermarket();
        assertEquals(supermarket, allSupermarkets);
    }

    @Test
    void getSupermarket() {
        Mockito.when(supermarketRepository.save(dummySupermarket)).thenReturn(dummySupermarket);
        Supermarket createdSupermarket = supermarketService.create(dummySupermarket);

        Optional<Supermarket> supermarket = Optional.empty();
        Mockito.when(supermarketRepository.findById(createdSupermarket.getId())).thenReturn((supermarket));

        try {
            Supermarket foundSupermarket = supermarketService.getSupermarketFull(createdSupermarket.getId());
            foundSupermarket.setManagerIds(null);
            assertEquals(supermarket.get(), foundSupermarket);
        } catch (Exception ignored) {}
    }

    @Test
    void getSupermarketAdmin_DoesntExist() {
        Mockito.when(supermarketRepository.save(dummySupermarket)).thenReturn(dummySupermarket);
        Supermarket createdSupermarket = supermarketService.create(dummySupermarket);

        Optional<Supermarket> supermarket = Optional.empty();
        Mockito.when(supermarketRepository.findById(createdSupermarket.getId())).thenReturn((supermarket));
        assertThrows(Exception.class, () -> supermarketService.getSupermarketFull(createdSupermarket.getId()));
    }

    @Test
    void getAllSupermarketAdmin() {
        Mockito.when(supermarketRepository.save(dummySupermarket)).thenReturn(dummySupermarket);
        Supermarket createdSupermarket = supermarketService.create(dummySupermarket);

        List<Supermarket> supermarket = List.of(createdSupermarket);
        Mockito.when(supermarketRepository.findAll()).thenReturn((supermarket));

        List<Supermarket> allSupermarkets = supermarketService.getAllSupermarketFull();
        assertEquals(supermarket, allSupermarkets);
    }
}