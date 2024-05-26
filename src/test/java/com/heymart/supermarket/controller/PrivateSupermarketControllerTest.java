package com.heymart.supermarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heymart.supermarket.model.Supermarket;
import com.heymart.supermarket.service.SupermarketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.mockito.BDDMockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PrivateSupermarketController.class)
@AutoConfigureMockMvc
public class PrivateSupermarketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupermarketService supermarketService;

    @Autowired
    private ObjectMapper objectMapper;

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
    }

    @Test
    void testCreateSupermarket() throws Exception {
        when(supermarketService.create(any(Supermarket.class))).thenReturn(dummySupermarket);

        mockMvc.perform(post("/api/admin/supermarket/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dummySupermarket))
        ).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(9223372036854775807L)));
    }

    @Test
    void testUpdateSupermarket() throws Exception {
        when(supermarketService.create(any(Supermarket.class))).thenReturn(dummySupermarket);
        supermarketService.create(dummySupermarket);

        mockMvc.perform(post("/api/admin/supermarket/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dummySupermarket))
        ).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(9223372036854775807L)));
    }

    @Test
    void testGetSupermarket() {
    }

    @Test
    void testGetSupermarket_DoesntExist() {
    }

    @Test
    void getAllSupermarket() {
    }
}