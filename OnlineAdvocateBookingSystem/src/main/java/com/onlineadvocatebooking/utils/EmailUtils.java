package com.onlineadvocatebooking.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailUtils {
@Autowired
private JavaMailSender emailSender;
public void sendsimpeMessage(String to,String subject,String text,List<String>List) {
	SimpleMailMessage message=new SimpleMailMessage();
	message.setFrom("luckysinghsolanki902@gmail.com");
	message.setSubject(subject);
	message.setText(text);
	if(List!=null && List.size()>0)
		message.setCc(getCcArray(List));
	emailSender.send(message);
	//message.setCc(getCcArray(List));
}
private String[] getCcArray(List<String>ccList) {
	String[] cc= new String[ccList.size()];
	for(int i=0 ;i<ccList.size();i++) {
		cc[i]=ccList.get(i);
	}
	return cc;
}
public void forgetMail(String to,String subject,String Password) throws  MessagingException{
	MimeMessage message=emailSender.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(message,true);
	helper.setFrom("luckysinghsolanki902@gmail.com");
	helper.setTo(to);
	helper.setSubject(subject);
	String htmlMsg = "<p><b>Your Login details for Advocate Management System</b><br><b>Email : </b>"+to+"<br><b>Password:</b>"+Password +"<br><a href=\"http://localhost:3306/\">Click Here to Login</a></p>";
	message.setContent(htmlMsg,"text/html");
	emailSender.send(message);
}
}
