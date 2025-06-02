package com.inventory.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.inventory.model.Stock;
import com.inventory.inventory.service.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetStockByProductId() throws Exception {
        Stock stock = new Stock(null, 1L, 15);

        Mockito.when(stockService.getStockByProductId(1L)).thenReturn(stock);

        mockMvc.perform(get("/api/stock/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.quantity").value(15));
    }

    @Test
    void shouldIncreaseStock() throws Exception {
        Long productId = 1L;
        int amountToAdd = 10;

        Stock updatedStock = new Stock(null, productId, 25);

        Mockito.when(stockService.incrementStock(productId, amountToAdd))
                .thenReturn(updatedStock);

        mockMvc.perform(post("/api/stock/{productId}/increase", productId)
                        .param("amount", String.valueOf(amountToAdd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.quantity").value(25));
    }

    @Test
    void shouldDecreaseStock() throws Exception {
        Long productId = 1L;
        int amountToSubtract = 5;

        Stock updatedStock = new Stock(null, productId, 10); // suponiendo que antes tenía 15

        Mockito.when(stockService.decrementStock(productId, amountToSubtract))
                .thenReturn(updatedStock);

        mockMvc.perform(post("/api/stock/{productId}/decrease", productId)
                        .param("amount", String.valueOf(amountToSubtract)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void shouldReturnBadRequestWhenStockIsInsufficient() throws Exception {
        Long productId = 1L;
        int amountToSubtract = 50;

        Mockito.when(stockService.decrementStock(productId, amountToSubtract))
                .thenThrow(new RuntimeException("Insufficient stock for productId: " + productId));

        mockMvc.perform(post("/api/stock/{productId}/decrease", productId)
                        .param("amount", String.valueOf(amountToSubtract)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient stock for productId: " + productId));
    }




}
