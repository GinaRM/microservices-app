package com.inventory.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.catalog.model.Product;
import com.inventory.catalog.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;




@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateProduct() throws Exception {
        Product inputProduct = new Product(null, "Laptop", "Gaming laptop", 1200.0);
        Product savedProduct = new Product(1L, "Laptop", "Gaming laptop", 1200.0);


        Mockito.when(productService.createProduct(any(Product.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedProduct.getId()))
                .andExpect(jsonPath("$.name").value(savedProduct.getName()))
                .andExpect(jsonPath("$.description").value(savedProduct.getDescription()))
                .andExpect(jsonPath("$.price").value(savedProduct.getPrice()));
    }

    @Test
    void shouldGetProductById() throws Exception {
        Product product = new Product(1L, "Teclado", "Teclado mecánico", 300.0);

        Mockito.when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        List<Product> products = List.of(
                new Product(1L, "Mouse", "Mouse inalámbrico", 150.0),
                new Product(2L, "Monitor", "Monitor 24 pulgadas", 750.0)
        );

        Mockito.when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(products.size()))
                .andExpect(jsonPath("$[0].name").value("Mouse"))
                .andExpect(jsonPath("$[1].price").value(750.0));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        Long productId = 1L;

        Mockito.doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());
    }


}
