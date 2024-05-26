package com.heymart.supermarket.controller;

import com.heymart.supermarket.model.Supermarket;
import com.heymart.supermarket.repository.SupermarketRepository;
import com.heymart.supermarket.service.SupermarketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PublicSupermarketController.class)
@AutoConfigureMockMvc
public class PublicSupermarketControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private SupermarketService supermarketService;

    @InjectMocks
    private PublicSupermarketController supermarketController;

    private Supermarket dummySupermarket;
    private Supermarket dummy2Supermarket;
    private Set<String> dummyManagerIds;

    @BeforeEach
    void setUp() {
        dummyManagerIds = new HashSet<>();
        dummyManagerIds.addAll(List.of(
                String.valueOf(Long.MAX_VALUE),
                String.valueOf(Long.MAX_VALUE-1),
                String.valueOf(Long.MAX_VALUE-2),
                String.valueOf(Long.MAX_VALUE-3)
        ));

        Supermarket.SupermarketBuilder builder = new Supermarket.SupermarketBuilder();
        builder.setName("Dummy Mart Test");
        builder.setColor("#cccccc");
        builder.setUrlName("dummy-mart-test");
        builder.setManagerIds(null);

        dummySupermarket = builder.build();
        dummySupermarket.setId(Long.MAX_VALUE);

        builder.setName("Dummy Mart 2");
        builder.setColor("#343434");
        builder.setUrlName("dummy mart2");
        builder.setManagerIds(dummyManagerIds);

        dummy2Supermarket = builder.build();
        dummy2Supermarket.setId(Long.MAX_VALUE-1);

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(supermarketController).build();
    }

    @Test
    void testGetSupermarket() {
        assertDoesNotThrow(() -> {
            String urlName = "dummy-mart-test";
            when(supermarketService.getSupermarket(urlName)).thenReturn(dummySupermarket);

            mockMvc.perform(get("/api/supermarket/get/{urlName}", "dummy-mart-test").contentType(MediaType.APPLICATION_JSON))
                    .andDo(print());
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.urlName", is("dummy-mart-test")));

            verify(supermarketService, times(1)).getSupermarket("dummy-mart-test");
        });
    }

    @Test
    void testGetSupermarket_DoesntExist() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(get("/api/supermarket/get/dummy-mart3"))
                    .andExpect(status().isNotFound());
        });
    }

    @Test
    void getAllSupermarket() {
    }
}