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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Adaptador de entrada REST
 * Convierte peticiones HTTP en llamadas a los puertos de entrada
 */
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Productos", description = "Operaciones relacionadas con la gestion de productos")
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

    @Operation(summary = "Crear un nuevo producto", description = "Crea un producto en la base de datos y retorna la información creada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
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

    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos disponibles en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = getProductUseCase.findAll();
        return ResponseEntity.ok(productMapper.toResponseList(products));
    }

    @Operation(summary = "Obtener un producto por ID", description = "Busca un producto específico. Usa caché si está disponible.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = getProductUseCase.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto con ID " + id + " no encontrado"));

        return ResponseEntity.ok(productMapper.toResponse(product));
    }

    @Operation(summary = "Actualizar un producto existente", description = "Actualiza los detalles de un producto existente en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductRequest request) {
        UpdateProductCommand command = commandMapper.toCommand(id, request);
        Product updatedProduct = updateProductUseCase.execute(command);
        return ResponseEntity.ok(productMapper.toResponse(updatedProduct));
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto específico de la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
