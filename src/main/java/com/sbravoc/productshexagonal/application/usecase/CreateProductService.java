package com.sbravoc.productshexagonal.application.usecase;

import com.sbravoc.productshexagonal.application.port.in.CreateProductCommand;
import com.sbravoc.productshexagonal.application.port.in.CreateProductUseCase;
import com.sbravoc.productshexagonal.application.port.out.ProductRepositoryPort;
import com.sbravoc.productshexagonal.domain.model.Product;

/**
 * Implementación del caso de uso para crear productos
 * Orquesta la lógica de negocio sin depender de frameworks específicos
 */
public class CreateProductService implements CreateProductUseCase {

    private final ProductRepositoryPort productRepository;

    public CreateProductService(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product execute(CreateProductCommand command) {
        // Usar el factory method del dominio que incluye validaciones
        Product product = Product.create(command.getName(), command.getPrice());

        // Persistir el producto
        return productRepository.save(product);
    }
}
