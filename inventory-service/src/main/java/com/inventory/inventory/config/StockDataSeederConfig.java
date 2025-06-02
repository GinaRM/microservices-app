package com.inventory.inventory.config;

import com.inventory.inventory.model.Stock;
import com.inventory.inventory.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockDataSeederConfig {
    @Bean
    CommandLineRunner seedStockData(StockRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Stock(null, 1L, 10));
                repository.save(new Stock(null, 2L, 25));
            }
        };
    }
}
