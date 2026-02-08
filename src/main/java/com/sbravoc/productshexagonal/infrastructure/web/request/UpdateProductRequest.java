package com.sbravoc.productshexagonal.infrastructure.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO para la petición de actualizar un producto
 * Pertenece a la capa de infraestructura (adaptador web)
 */
public class UpdateProductRequest {

    @Schema(description = "Nombre del producto", example = "Laptop Gamer", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @Schema(description = "Precio del producto", example = "1299.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive(message = "El precio debe ser mayor a cero")
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
