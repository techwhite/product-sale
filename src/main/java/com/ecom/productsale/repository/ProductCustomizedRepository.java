//package com.ecom.productsale.repository;
//
//import model.com.ecom.productsale.ProductEntity;
//import org.springframework.transaction.annotation.Transactional;
//
//public class ProductCustomizedRepository implements ProductRepository {
//
//    @Override
//    public boolean existsByName(String name) {
//        return false;
//    }
//
//    @Override
//    public ProductEntity findByName(String name) {
//        return null;
//    }
//
        /*
        should handle the exception and throw out by DatabaseAccessException
         */
//    @Transactional
//    public void transforMoney(int user1, int user2) {
//        try {
//            // deduce from user1
//            // add into user2
//        } catch(DataAccessException e) {
//            throw new DatabaseAccessException("Error accessing database", ex);
//        }
//    }
//}
