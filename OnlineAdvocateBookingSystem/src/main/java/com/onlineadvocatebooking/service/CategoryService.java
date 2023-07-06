package com.onlineadvocatebooking.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.onlineadvocatebooking.POJO.Category;

public interface CategoryService {
	ResponseEntity<String> addNewCatogery(Map<String,String>requestMap);
  ResponseEntity<List<Category>> getAllCategory(String filterValue );
  
  ResponseEntity<String> updateCategory(Map<String,String>requestMap);
}
