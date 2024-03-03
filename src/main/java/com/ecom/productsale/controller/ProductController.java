package com.ecom.productsale.controller;

import com.ecom.productsale.interceptor.RateLimit;
import com.ecom.productsale.model.ProductEntity;
import com.ecom.productsale.model.ProductRequest;
import com.ecom.productsale.model.ProductResponse;
import com.ecom.productsale.service.ProductService;
import com.ecom.productsale.util.ProductConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/*
 provide restful api endpoints for product processing

 http status best practice
 200: request successful without any error
 201: post request for created
 204: no content, used for not needing return anything like put/delete
 400: bad request with wrong params
 404: resource retrieved doesn't exist, e.g. can't find by getxxbyid()
 500: unexpected unknown runtime error
 */

@RateLimit(value = 5.0) // support 5 request per second
@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @Autowired
    ProductConverter productConverter;

    /*
     Stage 1 API endpoint: serves a list of products that can be sold
     */
    @GetMapping
    public ResponseEntity<Object> getAllProducts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                            @RequestParam(required = false, defaultValue = "100") Integer size) {
        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body("Invalid page or size params");
        }
        // find all products from db
        List<ProductEntity> dtoResList = productService.getAllProducts(page, size);

        // convert to http object
        List<ProductResponse> responses = dtoResList.stream()
                .map(res -> productConverter.dtoToResponse(res)).collect(Collectors.toList());

        return ResponseEntity.ok().body(responses);
    }

    /*
     Stage 2 API endpoint: creating a new product.
     should not save duplicated product, such as same product name
     */
    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody ProductRequest request) {
        if (request == null || !StringUtils.hasLength(request.getName())
                || request.getPrice() < 0) {
            return ResponseEntity.badRequest().body("Name or Price is Invalid");
        }

        // convert to dto object from http object
        ProductEntity dtoReq = productConverter.requestToDto(request);

        // Save
        ProductEntity dtoRes = productService.createProduct(dtoReq);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoRes.getId())
                .toUri();

        // convert to http object from dto object
        return ResponseEntity.created(location).body(productConverter.dtoToResponse(dtoRes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Integer id) {
        if (id < 0) {
            return ResponseEntity.badRequest().body("Invalid page or size params");
        }

        // find product from db
        ProductEntity dtoRes = productService.getProductById(id);

        // convert to http object
        ProductResponse response = productConverter.dtoToResponse(dtoRes);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<Object> updateProduct(@RequestBody ProductRequest request) {
        if (request == null || !StringUtils.hasLength(request.getName())
                || request.getPrice() < 0) {
            return ResponseEntity.badRequest().body("Name or Price is Invalid");
        }

        // convert to dto object from http object
        ProductEntity dtoReq = productConverter.requestToDto(request);

        // Save
        productService.updateProduct(dtoReq);

        // convert to http object from dto object
        return ResponseEntity.noContent().build();
    }

}
