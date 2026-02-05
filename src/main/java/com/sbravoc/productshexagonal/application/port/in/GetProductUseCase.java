package com.sbravoc.productshexagonal.application.port.in;

import com.sbravoc.productshexagonal.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada para consultar productos
 */
public interface GetProductUseCase {
    Optional<Product> findById(Long id);

    List<Product> findAll();
}
