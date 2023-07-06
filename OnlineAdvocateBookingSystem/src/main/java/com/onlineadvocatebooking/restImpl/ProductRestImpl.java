package com.onlineadvocatebooking.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.onlineadvocatebooking.constant.AdvocateConstant;
import com.onlineadvocatebooking.rest.ProductRest;
import com.onlineadvocatebooking.service.ProductService;
import com.onlineadvocatebooking.utils.AdvocateUtils;
import com.onlineadvocatebooking.wrapper.ProductWrapper;

public class ProductRestImpl implements ProductRest {

	@Autowired
	ProductService productservice;
	
	
	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			return productservice.addNewProduct(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProduct() {
		 try {
			 return productservice.getAllProduct();
		 }catch(Exception ex) {
			 ex.printStackTrace();			 
		 }
		return new ResponseEntity<> (new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR) ;
	}


	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		 try {
			 return productservice.updateProduct(requestMap);
		 }catch(Exception ex) {
			 ex.printStackTrace();
		 }
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
