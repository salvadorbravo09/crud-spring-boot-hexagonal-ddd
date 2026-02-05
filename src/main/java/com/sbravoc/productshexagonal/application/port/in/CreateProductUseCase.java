package com.sbravoc.productshexagonal.application.port.in;

import com.sbravoc.productshexagonal.domain.model.Product;

/**
 * Puerto de entrada para crear un producto
 * Define el contrato que la capa de aplicación expone al exterior
 */
public interface CreateProductUseCase {
    Product execute(CreateProductCommand command);
}
