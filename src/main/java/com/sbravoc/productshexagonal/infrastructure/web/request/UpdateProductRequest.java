package com.sbravoc.productshexagonal.infrastructure.web.request;

import java.math.BigDecimal;

/**
 * DTO para la petición de actualizar un producto
 * Pertenece a la capa de infraestructura (adaptador web)
 */
public class UpdateProductRequest {
    private String name;
    private BigDecimal price;

    public UpdateProductRequest() {
    }

    public UpdateProductRequest(String name, BigDecimal price) {
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
