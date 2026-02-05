package com.sbravoc.productshexagonal.domain.repository;

import com.sbravoc.productshexagonal.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);
}
