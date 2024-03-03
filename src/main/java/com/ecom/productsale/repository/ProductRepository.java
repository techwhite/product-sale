package com.ecom.productsale.repository;

import com.ecom.productsale.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
//    ProductEntity findBy
    boolean existsByName(String name);

    ProductEntity findByName(String name);

    /*
    exist by default.
    1. repository layer should keep simple for interaction with database
    2. unexpected logic should be processed in service layer. like no exist for delete
     */
    default void deleteByName(String name) {
        ProductEntity entity = findByName(name);
        deleteById(entity.getId());
    }
}