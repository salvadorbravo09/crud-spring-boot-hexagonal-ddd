package com.sbravoc.productshexagonal.infrastructure.web.controller;

import com.sbravoc.productshexagonal.application.port.in.CreateProductCommand;
import com.sbravoc.productshexagonal.application.port.in.CreateProductUseCase;
import com.sbravoc.productshexagonal.application.port.in.DeleteProductUseCase;
import com.sbravoc.productshexagonal.application.port.in.GetProductUseCase;
import com.sbravoc.productshexagonal.application.port.in.UpdateProductCommand;
import com.sbravoc.productshexagonal.application.port.in.UpdateProductUseCase;
import com.sbravoc.productshexagonal.domain.model.Product;
import com.sbravoc.productshexagonal.infrastructure.mapper.ProductCommandMapper;
import com.sbravoc.productshexagonal.infrastructure.mapper.ProductMapper;
import com.sbravoc.productshexagonal.infrastructure.web.request.CreateProductRequest;
import com.sbravoc.productshexagonal.infrastructure.web.request.UpdateProductRequest;
import com.sbravoc.productshexagonal.infrastructure.web.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Adaptador de entrada REST
 * Convierte peticiones HTTP en llamadas a los puertos de entrada
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final ProductMapper productMapper;
    private final ProductCommandMapper commandMapper;

    public ProductRestController(
            CreateProductUseCase createProductUseCase,
            GetProductUseCase getProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeleteProductUseCase deleteProductUseCase,
            ProductMapper productMapper,
            ProductCommandMapper commandMapper) {
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.productMapper = productMapper;
        this.commandMapper = commandMapper;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        CreateProductCommand command = commandMapper.toCommand(request);
        Product createdProduct = createProductUseCase.execute(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();

        return ResponseEntity.created(location).body(productMapper.toResponse(createdProduct));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = getProductUseCase.findAll();
        return ResponseEntity.ok(productMapper.toResponseList(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return getProductUseCase.findById(id)
                .map(product -> productMapper.toResponse(product))
                .map(productResponse -> ResponseEntity.ok(productResponse))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductRequest request) {
        UpdateProductCommand command = commandMapper.toCommand(id, request);
        Product updatedProduct = updateProductUseCase.execute(command);
        return ResponseEntity.ok(productMapper.toResponse(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
