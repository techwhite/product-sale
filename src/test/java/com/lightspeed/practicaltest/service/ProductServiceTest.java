package com.lightspeed.practicaltest.service;

import com.lightspeed.practicaltest.model.ProductEntity;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);

    @Autowired
    ProductService productService;

    // getAllProducts should not be null
    @Test
    public void testGetAllProducts() {
        List<ProductEntity> res = productService.getAllProducts(0, 2);
        assertNotNull(res);
    }

    // 1. should not new if already exist
    // 2. return detail info for new product
    @Test
    public void testSaveProduct() {
        // 1. save new one
        ProductEntity expected = new ProductEntity("testSaveProduct", BigDecimal.valueOf(100));
        ProductEntity result = productService.saveProduct(expected);
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getPrice(), result.getPrice());

        int id = result.getId();

        // 2. save duplicated one
        result = productService.saveProduct(expected);
        assertNull(result);

        // 3. clear test data
        productService.deleteProductById(id);
    }

    // 1. return result if having one in db
    // 2. return null if doesn't exist in db
    @Test
    public void testGetProductById() {
        // 1. not exist case
        ProductEntity result = productService.getProductById(-1);
        assertNull(result);

        // 2. save one
        ProductEntity in = new ProductEntity();
        in.setName("testGetProductById");
        in.setPrice(BigDecimal.valueOf(1.00));
        ProductEntity expected = productService.saveProduct(in);

        // 3. retrieve and compare
        result = productService.getProductById(expected.getId());
        assertEquals(expected, result);

        // 4. clear test data
        productService.deleteProductById(expected.getId());
    }

    @Test
    public void testDeleteProductById() {
        // 1. not exist case
        boolean result = productService.deleteProductById(-1);
        assertFalse(result);

        // 2. exist case
        // 2.1 save new one
        ProductEntity expected = new ProductEntity();
        expected.setName("testDeleteProductById");
        expected.setPrice(BigDecimal.valueOf(1.0));
        ProductEntity res = productService.saveProduct(expected);
        assertEquals(expected.getName(), res.getName());
        assertEquals(expected.getPrice(), res.getPrice());
        // 2.2 delete it
        assertTrue(productService.deleteProductById(res.getId()));
    }

    @Test
    public void testUpdateProduct() {
        // 1. not exist case
        ProductEntity expected = new ProductEntity();
        expected.setName("testUpdateProduct");
        expected.setPrice(BigDecimal.valueOf(12.0));
        assertNull(productService.updateProduct(expected));

        // 2. exist case
        // 2.1 save new one
        ProductEntity result = productService.saveProduct(expected);
        // 2.2 update new one
        result.setPrice(BigDecimal.valueOf(123.234));
        ProductEntity resultUpdated = productService.updateProduct(result);
        assertEquals(result, resultUpdated);

        // 3. clear test data
        productService.deleteProductById(resultUpdated.getId());
    }
}
