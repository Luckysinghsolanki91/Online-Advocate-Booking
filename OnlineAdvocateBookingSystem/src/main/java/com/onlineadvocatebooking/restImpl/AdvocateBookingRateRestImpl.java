package com.onlineadvocatebooking.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.onlineadvocatebooking.constant.AdvocateConstant;
import com.onlineadvocatebooking.rest.AdvocateBookingRateRest;
import com.onlineadvocatebooking.service.AdvocateBookingRateService;
import com.onlineadvocatebooking.utils.AdvocateUtils;

@RestController
public class AdvocateBookingRateRestImpl implements AdvocateBookingRateRest{
   @Autowired
   AdvocateBookingRateService advocateBookingRateService;
	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
		try {
		return   AdvocateBookingRateService.generateReport(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
