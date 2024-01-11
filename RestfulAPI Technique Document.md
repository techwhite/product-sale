# RESTful API Documentation

## Introduction

This document provides comprehensive information about the RESTful API project. It covers the various endpoints, 
request methods, request and response formats, authentication, and other relevant details.

## Authentication

The API requires authentication using API keys. Include the API key/value in the headers of each request:
### key
```text
Authorization
```
### value
```text
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmR5LnlvdSIsImlhdCI6MTcwNDU5NDY1M30" +
".RdBMCIUlpZJJ0-mHWNwwzJc70cuNOR2BoTz385GBtOA
```

## Endpoints

### Product Related
#### Base URL
```text
/products
```
#### 1. getAllProducts
GET /products
##### Description:
retrieve all product list and their detail info
##### Parameters:
none

#### 2. saveProduct
Post /products
##### Description:
Save a new Product and can be retrieved by #1 endpoint
##### Header
Content-Type: application/json

##### Request Body:
```json
{
    "name": "Chrome Toaster",
    "price": 100
}
```

### Sale Related
#### Base URL
/sales

#### 3. sale
Post /products
##### Description:
Sale all products in the request and return revenue
##### Header
Content-Type: application/json

##### Request Body:
```json
[
  {
    "id": 11,
    "quantity": 2
  },
  {
    "id": 13,
    "quantity": 1
  }
]
```
##### Response Example:
```json
{
    "requestList": [
        {
            "id": 11,
            "quantity": 2
        },
        {
            "id": 13,
            "quantity": 1
        }
    ],
    "total_price_item": [
        {
            "id": 11,
            "revenue": 200.00
        },
        {
            "id": 13,
            "revenue": 20.00
        }
    ],
    "total_price": 220.00
}
```
#### 4. saleWithDiscount
Post /products/discount
##### Description:
sale all products with discount in the request and return revenue with discount for each item
##### Header
Content-Type: application/json

##### Request Body:
```json
{
  "total_discount": 15,
  "requestList":
  [
    {
      "id": 11,
      "quantity": 2
    },
    {
      "id": 13,
      "quantity": 1
    }
  ]
}
```
#####  Response Example:
```json
{
    "request": {
        "total_discount": 15.0,
        "requestList": [
            {
                "id": 11,
                "quantity": 2
            },
            {
                "id": 13,
                "quantity": 1
            }
        ]
    },
    "discountItemList": [
        {
            "id": 11,
            "revenue": 186.363636363636363,
            "discount": 13.636363636363637
        },
        {
            "id": 13,
            "revenue": 18.6363636363636367,
            "discount": 1.3636363636363633
        }
    ],
    "total_revenue": 205.00
}
```

## Error Handling

The API follows standard HTTP status codes. In case of an error, additional details will be provided in the response body.

Example error response:

```json
{
  "code": 200,
  "status": "OK",
  "message": "Can't find one Product for the Request!",
  "stackTrace": null,
  "data": null
}
```

## Rate Limits
RateLimitInterceptor： define the rate limit logic. 

use page and size parameters to implement Pagination ability.
Example:
GET /products?page=1&per_page=10

## Versioning

## Changelog
version: v1
date: Jan 8th, 2024
Author: Andy You