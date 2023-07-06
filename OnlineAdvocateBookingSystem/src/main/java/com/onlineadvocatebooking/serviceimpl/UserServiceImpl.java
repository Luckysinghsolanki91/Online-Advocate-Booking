package com.onlineadvocatebooking.serviceimpl;

import java.util.ArrayList;
import java.util.List;
//import java.awt.Dialog;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
//import org.thymeleaf.util.Validate;

import com.google.common.base.Strings;
import com.onlineadvocatebooking.JWT.CustomerUserDetailsService;
import com.onlineadvocatebooking.JWT.JwtFilter;
//import com.onlineadvocatebooking.JWT.JwtFilter;
import com.onlineadvocatebooking.JWT.JwtUtil;
import com.onlineadvocatebooking.POJO.User;
import com.onlineadvocatebooking.constant.AdvocateConstant;
import com.onlineadvocatebooking.dao.UserDao;
import com.onlineadvocatebooking.rest.UserService;
import com.onlineadvocatebooking.rest.log;
import com.onlineadvocatebooking.utils.AdvocateUtils;
import com.onlineadvocatebooking.utils.EmailUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl<UserWrapper> implements UserService {
	@Autowired
	UserDao userDao;
	@Autowired
	AuthenticationManager authenticationManger;
	@Autowired
	CustomerUserDetailsService customerUserDetailsService;
	@Autowired
	JwtUtil  jwtUtil;
	@Autowired 
	JwtFilter jwtFilter;
	@Autowired
	EmailUtils emailUtils;
	
	public ResponseEntity<String> MysignUp(Map<String,String>requestMap){
		log.info("Inside signup {}", requestMap);
		try {
		if(validateSignUpMap(requestMap)) {
			User user = userDao.findByEmailId(requestMap.get("email"));
			if(Objects.isNull(user)) {
			userDao.save(getUserFromMap(requestMap));
			return AdvocateUtils.getResponseEnitiy("Successful regiesterd...", HttpStatus.OK);
			}else {
				return AdvocateUtils.getResponseEnitiy("Email already exits",HttpStatus.BAD_REQUEST);
			}
			
		}else {
			return AdvocateUtils.getResponseEnitiy( AdvocateConstant.INVALID_DATA,HttpStatus.BAD_REQUEST);
		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
			
		
	}
	
	
	private boolean validateSignUpMap(Map<String ,String>requestMap) {
		if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password")) {
			return true;
		}
			return false;
		
	}
		private User getUserFromMap(Map<String,String>requestMap) {
			User user=new User();
			user.setName(requestMap.get("name"));
			user.setContactNumber(requestMap.get("contactNumber"));
			user.setEmail(requestMap.get("email"));
			user.setPassword(requestMap.get("password"));
			user.setStatus("false");
			user.setRole("user");
			return user;
			
	
		}


		public ResponseEntity<String> Mylogin(Map<String, String> requestMap) {
			log.info("Inside Login", requestMap);
			try {
				Authentication auth = authenticationManger.authenticate(
						new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
						);
				if(auth.isAuthenticated()) {
					if(customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
						return new ResponseEntity<String>("{\"token\":\""+jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),customerUserDetailsService.getUserDetail().getRole())+"\"}",
								HttpStatus.OK);
					}else {
						return new ResponseEntity<String>("{\"message\":\""+"Wait for Admin Approval"+"\"}",HttpStatus.BAD_REQUEST);
					}
					
				}
			}catch(Exception ex){
				log.error("{}",ex);
				
			}
			return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials"+"\"}",HttpStatus.BAD_REQUEST);
			
		}

		@Override
		public ResponseEntity<List<UserWrapper>>getAllUser(){
			try {
				if(jwtFilter.isAdmin()) {
					//return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);
					//return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);
				}else {
					return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
				}
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}


		@Override
		public ResponseEntity signUp(Map requestMap) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public ResponseEntity login(Map requestMap) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public ResponseEntity<?> update(Map request) {
		   try {
			   if(jwtFilter.isAdmin()) {
				Optional<User>optional =   userDao.findById(Integer.parseInt((String) request.get("id")));
				if(!optional.isEmpty()) {
					userDao.updateStatus(request.get("status"),Integer.parseInt((String) request.get("Id")));
					sendMailToAllAdmin(request.get("status"),optional.get().getEmail(),userDao.getAllAdmin());
					return AdvocateUtils.getResponseEnitiy("User Status Update Sucessfully", HttpStatus.OK);
				}else {
					AdvocateUtils.getResponseEnitiy("User id  doesn't exist", HttpStatus.OK);
				}
			   }else {
				   return AdvocateUtils.getResponseEnitiy(AdvocateConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			   }
		   }catch(Exception ex) {
			   ex.printStackTrace();
		   }
			return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
		}


		private void sendMailToAllAdmin(Object object, String email, List<String> allAdmin) {
			// TODO Auto-generated method stub
			
		}


		private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
			// TODO Auto-generated method stub
			allAdmin.remove(jwtFilter.getCurrentUser());
			if(status != null && status.equalsIgnoreCase("true")) {
				emailUtils.sendsimpeMessage(jwtFilter.getCurrentUser(),"Account Approved..", "USER: -"+user+"\n is approved by \nADMIN:-" + jwtFilter.getCurrentUser(),allAdmin );
			}else {
				emailUtils.sendsimpeMessage(jwtFilter.getCurrentUser(),"Account Disabled..", "USER: -"+user+"\n is disabled by \nADMIN:-" + jwtFilter.getCurrentUser(),allAdmin );
				
			}
			
		}


		@Override
		public ResponseEntity checkToken() {
			return AdvocateUtils.getResponseEnitiy("true", HttpStatus.OK);
		}


		@Override
		public ResponseEntity changePassword(Map requestMap) {
			 try {
				 User userObj=userDao.findByEmail(jwtFilter.getCurrentUser());
				 if(!userObj.equals(null)) {
					 if(userObj.getPassword().equals(requestMap.get("oldPassword"))) {
						 userObj.setPassword((String) requestMap.get("newPassword"));
						 userDao.save(userObj);
						 return AdvocateUtils.getResponseEnitiy("Password Updated Successfully" , HttpStatus.OK);
					 }
					 return AdvocateUtils.getResponseEnitiy("Incorrect Old Password" , HttpStatus.BAD_REQUEST);
				 }
				 return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
			 return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		}


		@Override
		public ResponseEntity forgetPassword(Map requestMap) {
			try {
				 User user=userDao.findByEmail(requestMap.get("email"));
				 if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
					 emailUtils.forgetMail(user.getEmail(), " Credentials by Advocate Booking", user.getPassword());
					return AdvocateUtils.getResponseEnitiy("Check your email for cerdientials",HttpStatus.OK); 
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		}


//		@Override
//		public ResponseEntity signUp(Map requestMap) {
//			// TODO Auto-generated method stub
//			return null;
//		}


//		@Override
//		public ResponseEntity login(Map requestMap) {
//			// TODO Auto-generated method stub
//			return null;
//		}
	}
 
