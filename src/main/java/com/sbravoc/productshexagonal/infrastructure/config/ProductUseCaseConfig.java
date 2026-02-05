package com.sbravoc.productshexagonal.infrastructure.config;

import com.sbravoc.productshexagonal.application.port.in.CreateProductUseCase;
import com.sbravoc.productshexagonal.application.port.in.DeleteProductUseCase;
import com.sbravoc.productshexagonal.application.port.in.GetProductUseCase;
import com.sbravoc.productshexagonal.application.port.in.UpdateProductUseCase;
import com.sbravoc.productshexagonal.application.port.out.ProductRepositoryPort;
import com.sbravoc.productshexagonal.application.usecase.CreateProductService;
import com.sbravoc.productshexagonal.application.usecase.DeleteProductService;
import com.sbravoc.productshexagonal.application.usecase.GetProductService;
import com.sbravoc.productshexagonal.application.usecase.UpdateProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Spring para instanciar los casos de uso
 * Esta clase conecta los puertos con sus implementaciones
 */
@Configuration
public class ProductUseCaseConfig {

    @Bean
    public CreateProductUseCase createProductUseCase(ProductRepositoryPort productRepository) {
        return new CreateProductService(productRepository);
    }

    @Bean
    public GetProductUseCase getProductUseCase(ProductRepositoryPort productRepository) {
        return new GetProductService(productRepository);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase(ProductRepositoryPort productRepository) {
        return new UpdateProductService(productRepository);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase(ProductRepositoryPort productRepository) {
        return new DeleteProductService(productRepository);
    }
}
