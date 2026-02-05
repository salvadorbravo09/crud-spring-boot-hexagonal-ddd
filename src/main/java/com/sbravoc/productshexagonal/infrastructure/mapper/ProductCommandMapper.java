package com.sbravoc.productshexagonal.infrastructure.mapper;

import com.sbravoc.productshexagonal.application.port.in.CreateProductCommand;
import com.sbravoc.productshexagonal.application.port.in.UpdateProductCommand;
import com.sbravoc.productshexagonal.infrastructure.web.request.CreateProductRequest;
import com.sbravoc.productshexagonal.infrastructure.web.request.UpdateProductRequest;
import org.springframework.stereotype.Component;

/**
 * Implementación manual de mappers para comandos
 * Necesaria cuando MapStruct no puede generar el código automáticamente
 */
@Component
public class ProductCommandMapper {

    public CreateProductCommand toCommand(CreateProductRequest request) {
        if (request == null) {
            return null;
        }
        return new CreateProductCommand(request.getName(), request.getPrice());
    }

    public UpdateProductCommand toCommand(Long id, UpdateProductRequest request) {
        if (request == null || id == null) {
            return null;
        }
        return new UpdateProductCommand(id, request.getName(), request.getPrice());
    }
}
