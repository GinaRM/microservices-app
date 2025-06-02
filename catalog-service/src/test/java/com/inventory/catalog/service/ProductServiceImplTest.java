package com.inventory.catalog.service;

import com.inventory.catalog.model.Product;
import com.inventory.catalog.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testGetProductById_ShouldReturnProduct() {
        Product product = new Product(1L, "Laptop", "Gaming laptop", 1200.0);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertEquals("Laptop", result.getName());
        assertEquals("Gaming laptop", result.getDescription());
        assertEquals(1200.0, result.getPrice());
    }

    @Test
    void testGetProductById_ShouldThrowExceptionWhenNotFound() {
        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(99L);
        });

        assertEquals("Product not found with id: 99", exception.getMessage());
    }

    @Test
    void testGetAllProducts_ShouldReturnListOfProducts() {
        List<Product> products = List.of(
                new Product(1L, "Product 1", "Desc 1", 10.0),
                new Product(2L, "Product 2", "Desc 2", 20.0)
        );

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
    }

    @Test
    void testCreateProduct_ShouldSaveAndReturnProduct() {
        Product inputProduct = new Product(null, "New Product", "A description", 99.99);
        Product savedProduct = new Product(1L, "New Product", "A description", 99.99);

        when(productRepository.save(inputProduct)).thenReturn(savedProduct);

        Product result = productService.createProduct(inputProduct);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Product", result.getName());
        assertEquals("A description", result.getDescription());
        assertEquals(99.99, result.getPrice());
    }





}
