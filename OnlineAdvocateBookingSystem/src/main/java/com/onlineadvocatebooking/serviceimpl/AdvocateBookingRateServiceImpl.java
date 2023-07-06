package com.onlineadvocatebooking.serviceimpl;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.stream.Stream;

import org.bouncycastle.asn1.dvcs.Data;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.onlineadvocatebooking.JWT.JwtFilter;
import com.onlineadvocatebooking.POJO.AdvocateBookingRate;
import com.onlineadvocatebooking.constant.AdvocateConstant;
import com.onlineadvocatebooking.dao.AdvocateBookingRateDao;
import com.onlineadvocatebooking.rest.log;
import com.onlineadvocatebooking.service.AdvocateBookingRateService;
import com.onlineadvocatebooking.utils.AdvocateUtils;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class AdvocateBookingRateServiceImpl implements AdvocateBookingRateService {
	@Autowired
	JwtFilter jwtFilter;
	@Autowired 
	AdvocateBookingRateDao advocateDao;
  public ResponseEntity<String>generateReport(Map<String,Object>requestMap){
	  log.info("Inside generateReport ", null);
	try {
		String fileName;
		if(validateRequestMap(requestMap)) {
			if(requestMap.containsKey("isGenerate")&& !(Boolean)requestMap.get("isGenerate")) {
				fileName = (String) requestMap.get("uuid");
			}else {
				fileName = AdvocateUtils.getUUId();
				requestMap.put("uuid", fileName);
				insertAdvocateBookingRate(requestMap);
			}
			 String data="Name" + requestMap.get("name")+"\n"+"contactNumber"+requestMap.get("contactNumber")+"\n"+"Email:"+requestMap.get("email")+"\n"+"PaymentMethod:"+requestMap.get("paymetMethod")+"\n";
			Document document=new Document();
			 PdfWriter.getInstance(document,new FileOutputStream(AdvocateConstant.STORE_LOCATION+"\\"+fileName+".pdf"));
			 document.open();
			 setRectangleInPdf(document);
			 
			 Paragraph chunk=new Paragraph(" Online Advocate Booking..",getFont("Header"));
			 chunk.setAlignment(Element.ALIGN_CENTER);
			 document.add(chunk);
			 Paragraph paragraph=new Paragraph(data+"\n\n",getFont("Data"));
			 document.add(paragraph);
			 
			 PdfPTable table =new PdfPTable(5);
			 table.setWidthPercentage(100);
			 addTableHeader(table);
			JSONArray jsonArray =AdvocateUtils.getJsonArrayFromString((String)requestMap.get("AdvocateDetails")); 
			 for(int i=0;i<jsonArray.length();i++) {
				    addRow(table,AdvocateUtils.getMapFromJson(jsonArray.getString(i)));
			 }
			 
			 document.add(table);
			 Paragraph footer =new Paragraph("toatal"+requestMap.get("Total Amount ")+"\n"+"Thanks for visting!!!",getFont("Data"));
			 document.add(footer);
			 return new ResponseEntity<>("{\"uuid\""+fileName+"\"}",HttpStatus.OK);
		}
		return AdvocateUtils.getResponseEnitiy("Required data Not found..!!", HttpStatus.BAD_REQUEST);
		
	}catch(Exception ex) {
		ex.printStackTrace();
	}
	  return AdvocateUtils.getResponseEnitiy(AdvocateConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	  
  }
private void addRow(PdfPTable table, Map<String, Object> mapFromJson) {
	   log.info("Inside addRows", null);	
}
private void addTableHeader(PdfPTable table) {
	 log.info("Inside addTableHeader", null);
	 Stream.of("Name","Category","Role","Experiance","Rate","Sub Total").forEach(columnTitle ->{
		PdfPCell header=new PdfPCell();
		header.setBackgroundColor(BaseColor.GRAY);
		header.setBorderWidth(2);
		header.setPhrase(new Phrase(columnTitle));
		header.setBackgroundColor(BaseColor.YELLOW);
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(header);
	 });
	
}
private Font getFont(String type) {
	log.info("getFont", null);
	switch(type){
	case "Header":
	Font headerFont=FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
	headerFont.setStyle(Font.BOLD);
	return headerFont;
	case "Data":
		Font dataFont=FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
		dataFont.setStyle(Font.BOLD);
		return dataFont;
	case "Default":
		return new Font();
	}
	return null;
}
private void setRectangleInPdf(Document document) throws DocumentException {
	log.info("Inside set rectanglarPdf", null);
	Rectangle rect = new Rectangle(577, 825, 18, 15);
	rect.setBounds(rect);
	document.add((Element) rect);	
}

private void insertAdvocateBookingRate(Map<String, Object> requestMap) {
	try {
		AdvocateBookingRate Rate=new AdvocateBookingRate();
		Rate.setUUId((String) requestMap.get("uuid"));
		Rate.setName((String)requestMap.get("name"));
		Rate.setEmail((String)requestMap.get("email"));
		Rate.setContactNumber((String)requestMap.get("contactNumber"));
		Rate.setPaymentMethod((String)requestMap.get("paymentMethod"));
	   Rate.setTotal(Integer.parseInt((String)requestMap.get("totalAmount")));
		Rate.setAdvocateDetail((String) requestMap.get("AdvocateDetails"));
		Rate.setCreatedBy(jwtFilter.getCurrentUser());
		advocateDao.save(Rate);
	}catch(Exception ex) {
		ex.printStackTrace(); 
	}
	
}

private boolean validateRequestMap(Map<String, Object> requestMap) {
	
	return requestMap.containsKey("name") &&
			requestMap.containsKey("contactNumber") &&
			requestMap.containsKey("email")&&
			requestMap.containsKey("paymentMethod")&&
			requestMap.containsKey("AdvocateDetails") &&
			requestMap.containsKey("total");
}
}
