package com.ecom.productsale.util;

import com.ecom.productsale.model.ProductEntity;
import com.ecom.productsale.model.ProductRequest;
import com.ecom.productsale.model.ProductResponse;
import org.springframework.stereotype.Component;

/*
 This class defines few methods used for conversion between http object and dto objects
 requestToDto: converting http object to dto object
 dtoToResponse: converting dto object to http object
 */

@Component
public class ProductConverter {
    public ProductEntity requestToDto(ProductRequest request) {
        ProductEntity dtoReq = new ProductEntity();
        dtoReq.setName(request.getName());
        dtoReq.setPrice(request.getPrice());

        return dtoReq;
    }

    public ProductResponse dtoToResponse(ProductEntity dtoRes) {
        ProductResponse response = new ProductResponse();
        response.setName(dtoRes.getName());
        response.setPrice(dtoRes.getPrice());
        response.setId(dtoRes.getId());

        return response;
    }
}
