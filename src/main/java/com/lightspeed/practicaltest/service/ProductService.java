package com.lightspeed.practicaltest.service;

import com.lightspeed.practicaltest.model.ProductEntity;
import com.lightspeed.practicaltest.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// This class defines methods for processing products business logic

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    ProductRepository productRepository;

    // retrieve all products from db
    public List<ProductEntity> getAllProducts(int page, int size) {
        // findAll() always return non null list
        Page<ProductEntity> pageResult = productRepository.findAll(PageRequest.of(page, size));
        return pageResult.get().collect(Collectors.toList());
    }

    // save new product into db.
    // return null if having duplicated name in db
    // note: don't need adding @Transactional
    public ProductEntity saveProduct(ProductEntity product) {
        // check if already exist in db
        boolean exist = productRepository.existsByName(product.getName());
        if (exist) {
            return null;
        }

        return productRepository.save(product);
    }


    // delete one product by id. if doesn't exist, return false; or return true
    // don't need adding @Transactional since it's used for few data operations and wanting them take effect togerther
    public boolean deleteProductById(Integer id) {
        if (!productRepository.existsById(id)) {
            return false;
        }

        productRepository.deleteById(id);
        return true;

    }
}
