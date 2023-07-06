package com.onlineadvocatebooking.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface UserService<UserWrapper> {
  ResponseEntity<String> signUp(Map<String,String>requestMap);
  //ResponseEntity<String> login(Map<String,String>requestMap);
  ResponseEntity<List<UserWrapper>> getAllUser();
ResponseEntity<String> login(Map<String, String> requestMap);

ResponseEntity<String> update (Map<String,String>request);

ResponseEntity<String>checkToken();

ResponseEntity<String>changePassword(Map<String , String> requestMap);

ResponseEntity<String> forgetPassword(Map<String,String>rquestMap);

}
