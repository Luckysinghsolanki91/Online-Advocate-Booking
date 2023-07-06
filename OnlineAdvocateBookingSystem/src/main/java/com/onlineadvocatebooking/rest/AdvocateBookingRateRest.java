package com.onlineadvocatebooking.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path="/AdvocateBookingRate")
public interface AdvocateBookingRateRest {
  @PostMapping(path="/generateReport")
  ResponseEntity<String>generateReport(@RequestBody Map<String,Object>requestMap);
}
