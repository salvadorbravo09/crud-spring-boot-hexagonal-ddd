package com.sbravoc.productshexagonal.infrastructure.persistence.repository;

import com.sbravoc.productshexagonal.application.port.out.ProductRepositoryPort;
import com.sbravoc.productshexagonal.domain.model.Product;
import com.sbravoc.productshexagonal.infrastructure.entity.ProductEntity;
import com.sbravoc.productshexagonal.infrastructure.mapper.ProductEntityMapper;
import com.sbravoc.productshexagonal.infrastructure.persistence.jpa.SpringDataProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia que implementa el puerto de salida
 * Actúa como traductor entre el dominio y la infraestructura de JPA
 */
@Repository
public class JpaProductRepository implements ProductRepositoryPort {

    private final SpringDataProductRepository springDataRepository;
    private final ProductEntityMapper entityMapper;

    public JpaProductRepository(SpringDataProductRepository springDataRepository, ProductEntityMapper entityMapper) {
        this.springDataRepository = springDataRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    // Al guardar, actualizamos la caché con el nuevo producto usando su ID como clave
    // Usamos #result.id porque si es un CREATE, el ID viene en el resultado, no en input
    // unless -> Cachea el resultado A MENOS que el resultado sea nulo
    @CachePut(value = "products", key = "#result.id", unless = "#result == null")
    public Product save(Product product) {
        ProductEntity entity = entityMapper.toEntity(product);
        ProductEntity savedEntity = springDataRepository.save(entity);
        return entityMapper.toDomain(savedEntity);
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public Optional<Product> findById(Long id) {
        return springDataRepository.findById(id)
                .map(entity -> entityMapper.toDomain(entity));
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> entities = springDataRepository.findAll();
        return entityMapper.toDomainList(entities);
    }

    @Override
    @CacheEvict(value = "products", key = "#id")
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }
}
