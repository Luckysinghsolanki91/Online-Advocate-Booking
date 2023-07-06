package com.onlineadvocatebooking.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onlineadvocatebooking.JWT.JwtFilter;
import com.onlineadvocatebooking.POJO.Category;
import com.onlineadvocatebooking.POJO.Product;
import com.onlineadvocatebooking.constant.AdvocateConstant;
import com.onlineadvocatebooking.dao.ProductDao;
import com.onlineadvocatebooking.service.ProductService;
import com.onlineadvocatebooking.utils.AdvocateUtils;
import com.onlineadvocatebooking.wrapper.ProductWrapper;

@Service
public class ProductServiceImpl  implements ProductService{

	@Autowired
	ProductDao productDao;
	
	@Autowired
	JwtFilter jwtFilter;
	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(validateProductMap(requestMap,false)) {
				productDao.save(getProductFromMap(requestMap,false));
				return AdvocateUtils.getResponseEnitiy("Product Add successfully", HttpStatus.OK);
				}
				return AdvocateUtils.getResponseEnitiy(AdvocateConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
			}else {
				return AdvocateUtils.getResponseEnitiy(AdvocateConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("id") && validateId) {
				return true;
			}
			else if(!validateId) {
				return true;
				
			}
		}
		return false;
	}
	private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
		Category category=new Category();
		category.setId(Integer.parseInt(requestMap.get("categoryId")));
		Product product=new Product();
		if(isAdd) {
		product.setId(Integer.parseInt(requestMap.get("id")));	
		}else {
			product.setStatus("true");
		}
		product.setCategory(category);
		product.setName(requestMap.get("name"));
		product.setDescription(requestMap.get("description"));
		product.setrent(Integer.parseInt(requestMap.get("rent")));
		return product;
	}
	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProduct() {
		try {
			return new ResponseEntity<>(productDao.getAllProduct(),HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(validateProductMap(requestMap,true)) {
			Optional<Product> optional = 	productDao.findById(Integer.parseInt(requestMap.get("id")));
			if(!optional.isEmpty()) {
				       Product product =getProductFromMap(requestMap,true);
				       product.setStatus(optional.get().getStatus());
				       productDao.save(product);
				       return AdvocateUtils.getResponseEnitiy("Product Update Successfully", HttpStatus.OK);
			}else {
				return AdvocateUtils.getResponseEnitiy("Product id does not exist...", HttpStatus.OK);
			}
				}else {
					return AdvocateUtils.getResponseEnitiy(AdvocateConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
				}
			}else {
				return AdvocateUtils.getResponseEnitiy(AdvocateConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
