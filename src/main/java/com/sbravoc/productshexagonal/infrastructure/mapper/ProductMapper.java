package com.sbravoc.productshexagonal.infrastructure.mapper;

import com.sbravoc.productshexagonal.domain.model.Product;
import com.sbravoc.productshexagonal.infrastructure.web.response.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper para convertir entre modelos de dominio y DTOs de respuesta
 * MapStruct genera la implementación automáticamente
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Convertir una lista de productos (dominio) a una lista de respuestas (DTO)
    List<ProductResponse> toResponseList(List<Product> products);

    // Convertir un solo producto (dominio) a una respuesta (DTO)
    ProductResponse toResponse(Product product);
}
