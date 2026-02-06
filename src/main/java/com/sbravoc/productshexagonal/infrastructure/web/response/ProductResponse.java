package com.sbravoc.productshexagonal.infrastructure.web.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para la respuesta de un producto
 * Pertenece a la capa de infraestructura (adaptador web)
 */
public class ProductResponse {

    @Schema(description = "ID del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Laptop Gamer")
    private String name;

    @Schema(description = "Precio del producto", example = "1299.99")
    private BigDecimal price;

    @Schema(description = "Fecha y hora de creación del producto", example = "2024-06-15T10:15:30")
    private LocalDateTime createdAt;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, BigDecimal price, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
