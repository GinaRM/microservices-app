package com.inventory.catalog.config;

import com.inventory.catalog.model.Product;
import com.inventory.catalog.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeederConfig {
    @Bean
    CommandLineRunner seedProducts(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(Product.builder()
                        .name("Monitor")
                        .description("Monitor LED 24 pulgadas")
                        .price(800.0)
                        .build());

                repository.save(Product.builder()
                        .name("Teclado")
                        .description("Teclado mecánico retroiluminado")
                        .price(300.0)
                        .build());
            }
        };
    }
}
