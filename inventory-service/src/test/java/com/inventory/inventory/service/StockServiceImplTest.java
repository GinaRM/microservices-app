package com.inventory.inventory.service;

import com.inventory.inventory.client.CatalogClient;
import com.inventory.inventory.client.ProductDto;
import com.inventory.inventory.model.Stock;
import com.inventory.inventory.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {
    @Mock
    private StockRepository stockRepository;

    @Mock
    private CatalogClient catalogClient;


    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    void testIncrementStock() {
        Stock stock = new Stock(null, 1L, 10);

        when(catalogClient.getProductById(1L))
                .thenReturn(new ProductDto(1L, "Laptop", "Gaming", 1200.0));


        when(stockRepository.findByProductId(1L)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenAnswer(i -> i.getArgument(0));

        Stock result = stockService.incrementStock(1L, 5);

        assertEquals(15, result.getQuantity());
    }

    @Test
    void testDecrementStock() {
        // Given: stock con cantidad 10
        Stock stock = new Stock(null, 1L, 10);

        // Mock: el producto existe en catalog-service
        when(catalogClient.getProductById(1L))
                .thenReturn(new ProductDto(1L, "Laptop", "Gaming", 1200.0));

        // Mock: se encuentra el stock
        when(stockRepository.findByProductId(1L))
                .thenReturn(Optional.of(stock));

        // Mock: save devuelve el mismo objeto actualizado
        when(stockRepository.save(any(Stock.class)))
                .thenAnswer(i -> i.getArgument(0));

        // When: se solicita restar 4
        Stock result = stockService.decrementStock(1L, 4);

        // Then: queda 6
        assertEquals(6, result.getQuantity());
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {
        // Given: stock con solo 3 unidades
        Stock stock = new Stock(null, 1L, 3);

        // Mock: producto existe en catalog-service
        when(catalogClient.getProductById(1L))
                .thenReturn(new ProductDto(1L, "Laptop", "Gaming", 1200.0));

        // Mock: stock existe con cantidad insuficiente
        when(stockRepository.findByProductId(1L))
                .thenReturn(Optional.of(stock));

        // When & Then: esperamos excepción al intentar restar 5
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                stockService.decrementStock(1L, 5)
        );

        assertEquals("Insufficient stock for productId: 1", exception.getMessage());
    }


}
