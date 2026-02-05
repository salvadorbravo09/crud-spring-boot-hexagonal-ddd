package com.sbravoc.productshexagonal.application.usecase;

import com.sbravoc.productshexagonal.application.port.in.GetProductUseCase;
import com.sbravoc.productshexagonal.application.port.out.ProductRepositoryPort;
import com.sbravoc.productshexagonal.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del caso de uso para obtener productos
 */
public class GetProductService implements GetProductUseCase {

    private final ProductRepositoryPort productRepository;

    public GetProductService(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser un valor positivo");
        }
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
