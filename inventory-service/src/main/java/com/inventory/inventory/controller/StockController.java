package com.inventory.inventory.controller;

import com.inventory.inventory.model.Stock;
import com.inventory.inventory.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@Tag(name = "Stock API", description = "Operations for managing product stock")
public class StockController {
    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStock() {
        return ResponseEntity.ok(service.getAllStock());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Stock> getStock(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getStockByProductId(productId));
    }

    @PostMapping("/{productId}/increase")
    public ResponseEntity<Stock> increaseStock(@PathVariable Long productId,
                                               @RequestParam int amount) {
        return ResponseEntity.ok(service.incrementStock(productId, amount));
    }

    @PostMapping("/{productId}/decrease")
    public ResponseEntity<Stock> decreaseStock(@PathVariable Long productId,
                                               @RequestParam int amount) {
        return ResponseEntity.ok(service.decrementStock(productId, amount));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
