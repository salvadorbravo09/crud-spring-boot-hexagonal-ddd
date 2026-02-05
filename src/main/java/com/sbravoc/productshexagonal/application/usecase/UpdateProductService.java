package com.sbravoc.productshexagonal.application.usecase;

import com.sbravoc.productshexagonal.application.port.in.UpdateProductCommand;
import com.sbravoc.productshexagonal.application.port.in.UpdateProductUseCase;
import com.sbravoc.productshexagonal.application.port.out.ProductRepositoryPort;
import com.sbravoc.productshexagonal.domain.model.Product;

import java.util.NoSuchElementException;

/**
 * Implementación del caso de uso para actualizar productos
 */
public class UpdateProductService implements UpdateProductUseCase {

    private final ProductRepositoryPort productRepository;

    public UpdateProductService(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product execute(UpdateProductCommand command) {
        // Buscar el producto existente
        Product product = productRepository.findById(command.getId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Producto con ID " + command.getId() + " no encontrado"));

        // Usar el método de negocio del dominio
        product.update(command.getName(), command.getPrice());

        // Persistir los cambios
        return productRepository.save(product);
    }
}
