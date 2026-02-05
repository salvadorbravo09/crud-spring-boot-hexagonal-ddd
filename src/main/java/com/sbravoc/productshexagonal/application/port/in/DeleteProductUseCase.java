package com.sbravoc.productshexagonal.application.port.in;

/**
 * Puerto de entrada para eliminar un producto
 */
public interface DeleteProductUseCase {
    void execute(Long id);
}
