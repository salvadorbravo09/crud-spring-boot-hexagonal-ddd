package com.sbravoc.productshexagonal.infrastructure.web.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * DTO para la petición de crear un producto
 * Pertenece a la capa de infraestructura (adaptador web)
 */
public class CreateProductRequest {

    @Schema(description = "Nombre del producto", example = "Laptop Gamer", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Precio del producto", example = "1299.99", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    public CreateProductRequest() {
    }

    public CreateProductRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
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
}
