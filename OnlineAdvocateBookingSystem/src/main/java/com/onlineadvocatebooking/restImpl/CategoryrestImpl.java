package com.onlineadvocatebooking.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.onlineadvocatebooking.POJO.Category;
import com.onlineadvocatebooking.constant.AdvocateConstant;
import com.onlineadvocatebooking.rest.CategoryRest;
import com.onlineadvocatebooking.service.CategoryService;
import com.onlineadvocatebooking.utils.AdvocateUtils;

public class CategoryrestImpl  implements CategoryRest{

	@Autowired
	CategoryService categoryService;
	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		try {
			return categoryService.addNewCatogery(requestMap);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
		try {
			return categoryService.getAllCategory(filterValue);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return  new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR );
	}
	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
		try {
			return categoryService.updateCategory(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
