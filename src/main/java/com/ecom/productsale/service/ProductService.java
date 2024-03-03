package com.ecom.productsale.service;

import com.ecom.productsale.model.ProductEntity;
import com.ecom.productsale.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 This class defines methods for processing products business logic

 The best practice for exception is to define as most in this layer based on business and return them to controller
 */

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    ProductRepository productRepository;

    // retrieve all products from db
    public List<ProductEntity> getAllProducts(int page, int size) {
        // findAll() always return non null list
        // will convert to sql using offset limit
        Page<ProductEntity> pageResult = productRepository.findAll(PageRequest.of(page, size));
        return pageResult.get().collect(Collectors.toList());
    }

    // retrieve product by id
    public ProductEntity getProductById(int id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new EntityNotFoundException("No Product found for id: " + id);
        }

        return product.orElse(null);
    }

    // save new product into db.
    // return null if having duplicated name in db
    // note: don't need adding @Transactional
    public ProductEntity createProduct(ProductEntity product) {
        // check if already exist in db
        boolean exist = productRepository.existsByName(product.getName());
        if (exist) {
            throw new DuplicateKeyException("Already exist product for name: " + product.getName());
        }

        return productRepository.save(product);
    }

    public ProductEntity updateProduct(ProductEntity product) {
        // check if already exist in db
        ProductEntity productEntity = productRepository.findByName(product.getName());
        if (productEntity == null) {
            throw new EntityNotFoundException("No product found for update: " + product.getName());
        }

        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());

        return productRepository.save(productEntity);
    }


    // delete one product by id. if doesn't exist, return false; or return true
    // don't need adding @Transactional since it's used for few data operations and wanting them take effect togerther
    public boolean deleteProductById(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("No product found for delete: " + id);
        }

        productRepository.deleteById(id);
        return true;

    }

    public boolean deleteProductByName(String name) {
        if (!productRepository.existsByName(name)) {
            throw new EntityNotFoundException("No product found for delete: " + name);
        }

        productRepository.deleteByName(name);
        return true;
    }
}
