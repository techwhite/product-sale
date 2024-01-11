package com.lightspeed.practicaltest.repository;

import com.lightspeed.practicaltest.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
//    ProductEntity findBy
    boolean existsByName(String name);

    ProductEntity findByName(String name);
}