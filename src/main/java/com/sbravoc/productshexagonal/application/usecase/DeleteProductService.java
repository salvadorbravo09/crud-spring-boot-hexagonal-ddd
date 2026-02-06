package com.sbravoc.productshexagonal.application.usecase;

import com.sbravoc.productshexagonal.application.port.in.DeleteProductUseCase;
import com.sbravoc.productshexagonal.application.port.out.ProductRepositoryPort;

import java.util.NoSuchElementException;

/**
 * Implementación del caso de uso para eliminar productos
 */
public class DeleteProductService implements DeleteProductUseCase {

    private final ProductRepositoryPort productRepository;

    public DeleteProductService(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void execute(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser un valor positivo");
        }

        // Verificar que el producto existe antes de eliminar
        // TODO: Manejar esto con una sola llamada a la base de datos para evitar dos consultas
        productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Producto con ID " + id + " no encontrado"));

        productRepository.deleteById(id);
    }
}
