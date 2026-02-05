package com.sbravoc.productshexagonal.application.port.out;

import com.sbravoc.productshexagonal.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para persistencia de productos
 * Define el contrato que la infraestructura debe implementar
 */
public interface ProductRepositoryPort {
    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);
}
