package com.onlineadvocatebooking.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.onlineadvocatebooking.wrapper.ProductWrapper;

public interface ProductService {
   ResponseEntity<String> addNewProduct(Map<String,String> requestMap);
   
   ResponseEntity<List<ProductWrapper>> getAllProduct() ;
   ResponseEntity<String> updateProduct(Map<String,String> requestMap);
}
