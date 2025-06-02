package com.inventory.inventory.service;

import com.inventory.inventory.model.Stock;

import java.util.List;

public interface StockService {
    Stock getStockByProductId(Long productId);
    Stock incrementStock(Long productId, int amount);
    Stock decrementStock(Long productId, int amount);
    List<Stock> getAllStock();
}
