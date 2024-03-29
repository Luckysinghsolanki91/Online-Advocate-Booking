package com.onlineadvocatebooking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineadvocatebooking.POJO.Product;
import com.onlineadvocatebooking.wrapper.ProductWrapper;

public interface ProductDao  extends JpaRepository<Product , Integer>{

	List<ProductWrapper> getAllProduct();
 
}
