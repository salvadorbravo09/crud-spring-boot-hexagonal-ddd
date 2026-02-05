package com.sbravoc.productshexagonal.infrastructure.mapper;

import com.sbravoc.productshexagonal.domain.model.Product;
import com.sbravoc.productshexagonal.infrastructure.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre entidades JPA y modelos de dominio
 * Implementación manual necesaria porque Product es inmutable
 */
@Component
public class ProductEntityMapper {

    // Convertir una lista de entidades a una lista de modelos de dominio
    public List<Product> toDomainList(List<ProductEntity> entities) {
        return entities
                .stream()
                .map(entity -> toDomain(entity))
                .collect(Collectors.toList());
    }

    // Convertir una entidad a un modelo de dominio
    public Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        Product product = new Product(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getCreatedAt()
        );
        return product;
    }

    // Convertir un modelo de dominio a una entidad
    public ProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setCreatedAt(product.getCreatedAt());
        return entity;
    }
}
