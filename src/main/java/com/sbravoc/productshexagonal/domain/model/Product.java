package com.sbravoc.productshexagonal.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public Product() {
    }

    public Product(Long id, String name, BigDecimal price, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }

    // Factory method para crear un nuevo producto
    public static Product create(String name, BigDecimal price) {
        validateName(name);
        validatePrice(price);

        Product product = new Product();
        product.name = name;
        product.price = price;
        product.createdAt = LocalDateTime.now();
        return product;
    }

    // Validaciones de negocio
    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("El nombre del producto no puede exceder 100 caracteres");
        }
    }

    private static void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("El precio no puede ser nulo");
        }
        // compareTo devuelve -1 si es menor, 0 si es igual, 1 si es mayor
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }

    // Método de negocio para actualizar el producto
    public void update(String name, BigDecimal price) {
        validateName(name);
        validatePrice(price);
        this.name = name;
        this.price = price;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Solo setter para ID (necesario para la persistencia)
    public void setId(Long id) {
        this.id = id;
    }
}
