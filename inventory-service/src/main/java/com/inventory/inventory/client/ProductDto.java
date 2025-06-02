package com.inventory.inventory.client;

public record ProductDto(
        Long id,
        String name,
        String description,
        Double price
) {}
