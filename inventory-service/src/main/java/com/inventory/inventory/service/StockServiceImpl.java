package com.inventory.inventory.service;

import com.inventory.inventory.client.CatalogClient;
import com.inventory.inventory.model.Stock;
import com.inventory.inventory.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService{

    private final StockRepository repository;
    private final CatalogClient catalogClient;

    public StockServiceImpl(StockRepository repository, CatalogClient catalogClient) {

        this.repository = repository;
        this.catalogClient = catalogClient;

    }

    @Override
    public Stock getStockByProductId(Long productId) {
        return repository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Stock not found for productId: " + productId));
    }

    @Override
    public Stock incrementStock(Long productId, int amount) {
        validateProductExists(productId);

        Stock stock = repository.findByProductId(productId)
                .orElse(new Stock(null, productId, 0));

        stock.setQuantity(stock.getQuantity() + amount);
        return repository.save(stock);
    }

    private void validateProductExists(Long productId) {
        try {
            catalogClient.getProductById(productId);
        } catch (Exception e) {
            throw new RuntimeException("Product not found in catalog-service: " + productId);
        }
    }

    @Override
    public Stock decrementStock(Long productId, int amount) {
        validateProductExists(productId);
        Stock stock = repository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Stock not found for productId: " + productId));

        if (stock.getQuantity() < amount) {
            throw new RuntimeException("Insufficient stock for productId: " + productId);
        }

        stock.setQuantity(stock.getQuantity() - amount);
        return repository.save(stock);
    }

    @Override
    public List<Stock> getAllStock() {
        return repository.findAll();
    }
}
