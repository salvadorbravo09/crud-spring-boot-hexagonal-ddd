package com.sbravoc.productshexagonal.infrastructure.persistence.repository;

import com.sbravoc.productshexagonal.application.port.out.ProductRepositoryPort;
import com.sbravoc.productshexagonal.domain.model.Product;
import com.sbravoc.productshexagonal.infrastructure.entity.ProductEntity;
import com.sbravoc.productshexagonal.infrastructure.mapper.ProductEntityMapper;
import com.sbravoc.productshexagonal.infrastructure.persistence.jpa.SpringDataProductRepository;
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
    public Product save(Product product) {
        ProductEntity entity = entityMapper.toEntity(product);
        ProductEntity savedEntity = springDataRepository.save(entity);
        return entityMapper.toDomain(savedEntity);
    }

    @Override
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
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }
}
