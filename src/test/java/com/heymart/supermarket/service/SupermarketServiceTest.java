package com.heymart.supermarket.service;

import com.heymart.supermarket.model.Supermarket;
import com.heymart.supermarket.repository.SupermarketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupermarketServiceTest {
    @Mock
    private SupermarketRepository supermarketRepository;

    @InjectMocks
    private SupermarketService supermarketService;

    private Supermarket dummySupermarket;
    private Set<String> dummyManagerIds;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dummyManagerIds = new HashSet<>();
        dummyManagerIds.addAll(List.of(
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

        dummySupermarket = builder.build();
        dummySupermarket.setId(Long.MAX_VALUE);
    }

    @Test
    void createSupermarket() {
        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummySupermarket);
        Supermarket createdSupermarket = supermarketService.create(dummySupermarket);

        assertEquals(dummySupermarket, createdSupermarket);
        verify(supermarketRepository, times(1)).save(any(Supermarket.class));
    }

    @Test
    void updateSupermarket() {
        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummySupermarket);
        supermarketService.create(dummySupermarket);
        verify(supermarketRepository, times(1)).save(dummySupermarket);

        dummySupermarket.setName("Hey Hey!!");
        dummySupermarket.setColor("#666777");
        dummySupermarket.setUrlName("hey-hey");

        when(supermarketRepository.findById(Long.MAX_VALUE)).thenReturn(Optional.of(dummySupermarket));

        assertDoesNotThrow(() -> {
            Supermarket savedUpdated = supermarketService.update(Long.MAX_VALUE, dummySupermarket);

            assertEquals(dummySupermarket, savedUpdated);
            verify(supermarketRepository, times(2)).save(dummySupermarket);
        });
    }

    @Test
    void updateSupermarket_DoesntExist() {
        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummySupermarket);
        supermarketService.create(dummySupermarket);
        verify(supermarketRepository, times(1)).save(dummySupermarket);

        dummySupermarket.setName("Hey Hey!!");
        dummySupermarket.setColor("#666777");
        dummySupermarket.setUrlName("hey-hey");

        assertThrows(Exception.class, () -> supermarketService.update(767L, dummySupermarket));
    }

    @Test
    void deleteSupermarket() {
        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummySupermarket);
        supermarketService.create(dummySupermarket);
        verify(supermarketRepository, times(1)).save(dummySupermarket);

        when(supermarketRepository.findById(Long.MAX_VALUE)).thenReturn(Optional.of(dummySupermarket));
        assertDoesNotThrow(() -> {
            supermarketService.delete(Long.MAX_VALUE);
            verify(supermarketRepository, times(1)).deleteById(Long.MAX_VALUE);
        });
    }

    @Test
    void deleteSupermarket_DoesntExist() {
        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummySupermarket);
        supermarketService.create(dummySupermarket);
        verify(supermarketRepository, times(1)).save(dummySupermarket);

        when(supermarketRepository.findById(Long.MAX_VALUE)).thenReturn(Optional.of(dummySupermarket));
        assertThrows(Exception.class, () -> supermarketService.delete(767L));
    }

    @Test
    void getSupermarketPublic() {
        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummySupermarket);
        supermarketService.create(dummySupermarket);
        verify(supermarketRepository, times(1)).save(dummySupermarket);

        Optional<Supermarket> supermarket = Optional.of(dummySupermarket);
        Supermarket mockSupermarket = supermarket.get();
        mockSupermarket.setManagerIds(null); // simulate public get (no data of managers)
        when(supermarketRepository.findByUrlName("dummy-mart")).thenReturn(supermarket);

        assertDoesNotThrow(() -> {
            Supermarket foundSupermarket = supermarketService.getSupermarket("dummy-mart");
            assertEquals(mockSupermarket, foundSupermarket);
            verify(supermarketRepository, times(1)).findByUrlName("dummy-mart");
        });
    }

    @Test
    void getSupermarketPublic_DoesntExist() {
        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummySupermarket);
        supermarketService.create(dummySupermarket);
        verify(supermarketRepository, times(1)).save(dummySupermarket);

        Optional<Supermarket> supermarket = Optional.empty();
        when(supermarketRepository.findByUrlName("dummy-mart2")).thenReturn(supermarket);

        assertThrows(Exception.class, () -> supermarketService.getSupermarket("dummy-mart2"));
    }

    @Test
    void getAllSupermarketPublic() {
        // simulate 2 supermarket saves
        Supermarket.SupermarketBuilder builder = new Supermarket.SupermarketBuilder();
        builder.setName("Dummy Mart2");
        builder.setColor("#434343");
        builder.setUrlName("dummy mart2");
        builder.setManagerIds(dummyManagerIds);

        Supermarket dummy2Supermarket = builder.build();
        dummy2Supermarket.setId(Long.MAX_VALUE-1);

        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummySupermarket);
        supermarketService.create(dummySupermarket);
        verify(supermarketRepository, times(1)).save(dummySupermarket);

        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummy2Supermarket);
        supermarketService.create(dummy2Supermarket);
        verify(supermarketRepository, times(1)).save(dummy2Supermarket);

        List<Supermarket> supermarkets = List.of(dummySupermarket, dummy2Supermarket);
        when(supermarketRepository.findAll()).thenReturn((supermarkets));

        for (Supermarket s : supermarkets) {
            s.setManagerIds(null); // simulate public get (no data of managers)
        }

        List<Supermarket> allSupermarkets = supermarketService.getAllSupermarket();
        assertEquals(supermarkets, allSupermarkets);
        verify(supermarketRepository, times(1)).findAll();
    }

    @Test
    void getSupermarket() {
        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummySupermarket);
        supermarketService.create(dummySupermarket);
        verify(supermarketRepository, times(1)).save(dummySupermarket);

        Optional<Supermarket> supermarket = Optional.of(dummySupermarket);
        Supermarket mockSupermarket = supermarket.get();
        when(supermarketRepository.findById(Long.MAX_VALUE)).thenReturn(supermarket);

        assertDoesNotThrow(() -> {
            Supermarket foundSupermarket = supermarketService.getSupermarketFull(Long.MAX_VALUE);
            assertEquals(mockSupermarket, foundSupermarket);
            verify(supermarketRepository, times(1)).findById(Long.MAX_VALUE);
        });
    }

    @Test
    void getSupermarketAdmin_DoesntExist() {
        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummySupermarket);
        supermarketService.create(dummySupermarket);
        verify(supermarketRepository, times(1)).save(dummySupermarket);

        Optional<Supermarket> supermarket = Optional.empty();
        when(supermarketRepository.findById(Long.MAX_VALUE-1)).thenReturn(supermarket);

        assertThrows(Exception.class, () -> supermarketService.getSupermarketFull(Long.MAX_VALUE-1));
    }

    @Test
    void getAllSupermarketAdmin() {
        // simulate 2 supermarket saves
        Supermarket.SupermarketBuilder builder = new Supermarket.SupermarketBuilder();
        builder.setName("Dummy Mart2");
        builder.setColor("#434343");
        builder.setUrlName("dummy mart2");
        builder.setManagerIds(dummyManagerIds);

        Supermarket dummy2Supermarket = builder.build();
        dummy2Supermarket.setId(Long.MAX_VALUE-1);

        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummySupermarket);
        supermarketService.create(dummySupermarket);
        verify(supermarketRepository, times(1)).save(dummySupermarket);

        when(supermarketRepository.save(any(Supermarket.class))).thenReturn(dummy2Supermarket);
        supermarketService.create(dummy2Supermarket);
        verify(supermarketRepository, times(1)).save(dummy2Supermarket);

        List<Supermarket> supermarkets = List.of(dummySupermarket, dummy2Supermarket);
        when(supermarketRepository.findAll()).thenReturn((supermarkets));

        List<Supermarket> allSupermarkets = supermarketService.getAllSupermarketFull();
        assertEquals(supermarkets, allSupermarkets);
        verify(supermarketRepository, times(1)).findAll();
    }
}