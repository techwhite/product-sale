package com.lightspeed.practicaltest.controller;

import com.lightspeed.practicaltest.interceptor.RateLimit;
import com.lightspeed.practicaltest.model.ErrorResponse;
import com.lightspeed.practicaltest.util.ProductConverter;
import com.lightspeed.practicaltest.model.ProductEntity;
import com.lightspeed.practicaltest.model.ProductRequest;
import com.lightspeed.practicaltest.model.ProductResponse;
import com.lightspeed.practicaltest.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// provide restful api endpoints for product processing

@RateLimit(value = 5.0) // support 5 request per second
@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @Autowired
    ProductConverter productConverter;

    // Stage 1 API endpoint: serves a list of products that can be sold
    @GetMapping
    public ResponseEntity<?> getAllProducts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                            @RequestParam(required = false, defaultValue = "100") Integer size) {
        if (page < 0 || size <= 0) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid page or size params"),
                    HttpStatus.BAD_REQUEST);
        }
        // find all products from db
        List<ProductEntity> dtoResList = productService.getAllProducts(page, size);

        // convert to http object
        List<ProductResponse> responses = dtoResList.stream()
                .map(res -> productConverter.dtoToResponse(res)).collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // Stage 2 API endpoint: creating a new product.
    // should not save duplicated product, such as same product name
    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody ProductRequest request) {
        if (request == null || !StringUtils.hasLength(request.getName())
                || request.getPrice() == null || request.getPrice().doubleValue() < 0) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Name or Price is Invalid", "",
                    request), HttpStatus.BAD_REQUEST);
        }

        // convert to dto object from http object
        ProductEntity dtoReq = productConverter.requestToDto(request);

        // Save
        ProductEntity dtoRes = productService.saveProduct(dtoReq);

        if (dtoRes == null) {
            // have exist in db
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Product with same name already exists!"), HttpStatus.UNAUTHORIZED);
        }

        // convert to http object from dto object
        return ResponseEntity.ok().body(productConverter.dtoToResponse(dtoRes));
    }

}
