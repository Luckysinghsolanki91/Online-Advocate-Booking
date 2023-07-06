package com.onlineadvocatebooking.rest;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.onlineadvocatebooking.constant.AdvocateConstant;
import com.onlineadvocatebooking.utils.AdvocateUtils;
import com.onlineadvocatebooking.wrapper.UserWrapper;

public   class UserRestImpl  implements UserRest{

	@Autowired
	UserService userService;

	@Override
	public ResponseEntity<String> signUp1(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		try {
			return userService.signUp(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		//return new ResponseEntity<String>()
		return AdvocateUtils.getResponseEnitiy( AdvocateConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	
	}

	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
		try {
			return userService.login(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy( AdvocateConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<UserWrapper> getAllUser() {
		try{
			return userService.getAllUser();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		//return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
		return null;
	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {
			return userService.update(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> checkToken() {
		try {
			return userService.checkToken();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
		try {
			return userService.changePassword(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> forgetPassword(Map<String, String> requestMap) {
		try {
			return userService.forgetPassword(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}