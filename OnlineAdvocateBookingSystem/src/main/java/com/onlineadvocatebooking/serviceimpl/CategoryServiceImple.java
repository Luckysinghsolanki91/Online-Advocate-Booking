package com.onlineadvocatebooking.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.onlineadvocatebooking.JWT.JwtFilter;
import com.onlineadvocatebooking.POJO.Category;
import com.onlineadvocatebooking.constant.AdvocateConstant;
import com.onlineadvocatebooking.dao.CategoryDao;
import com.onlineadvocatebooking.rest.log;
import com.onlineadvocatebooking.service.CategoryService;
import com.onlineadvocatebooking.utils.AdvocateUtils;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class CategoryServiceImple implements CategoryService {

	@Autowired
	CategoryDao categoryDao;
	@Autowired
	JwtFilter jwtFilter;
	
	@Override
	public ResponseEntity<String> addNewCatogery(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(validateCategoryMap(requestMap,false)) {
					categoryDao.save(getCategoryFromMap(requestMap,false));
					return AdvocateUtils.getResponseEnitiy("Cateory Added sucessfully", HttpStatus.OK);
				}
			}else {
				return AdvocateUtils.getResponseEnitiy(AdvocateConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
		if(requestMap.containsKey("id")&& validateId) {
			return true;
		}else if(!validateId){
			return true;
		}
		return false;
	}
	private Category getCategoryFromMap(Map<String,String>requestMap,Boolean isAdd) {
		Category category =new Category();
		if(isAdd) {
			category.setId(Integer.parseInt(requestMap.get("id")));
		}
		category.setName(requestMap.get("name"));
		return category;
		
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
		try {
			if(!Strings.isNullOrEmpty(filterValue)&& filterValue.equalsIgnoreCase("true")) {
				log.info("inside if", null);
				
				return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);
			}
			return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(validateCategoryMap(requestMap,true)) {
				Optional optional =	categoryDao.findById(Integer.parseInt(requestMap.get("id")));
				if(!optional.isEmpty()){
				    categoryDao.save(getCategoryFromMap(requestMap,true));
				    return AdvocateUtils.getResponseEnitiy("Category Updated successfully", HttpStatus.OK);
				}else {
					return AdvocateUtils.getResponseEnitiy("Category Id doesn't exit's", HttpStatus.OK);
				}
				}
				return AdvocateUtils.getResponseEnitiy(AdvocateConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
			}else {
				return AdvocateUtils.getResponseEnitiy(AdvocateConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
