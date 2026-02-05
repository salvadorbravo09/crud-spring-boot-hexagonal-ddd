package com.sbravoc.productshexagonal.application.port.in;

import com.sbravoc.productshexagonal.domain.model.Product;

/**
 * Puerto de entrada para actualizar un producto
 */
public interface UpdateProductUseCase {
    Product execute(UpdateProductCommand command);
}
