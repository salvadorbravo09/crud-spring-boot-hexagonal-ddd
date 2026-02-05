package com.sbravoc.productshexagonal.infrastructure.persistence.jpa;

import com.sbravoc.productshexagonal.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
}
