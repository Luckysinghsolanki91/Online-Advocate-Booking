package com.onlineadvocatebooking.POJO;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="AdvocateBookingRate")
public class AdvocateBookingRate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name ="uuid")
	private String uuid;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="contactnumber")
	private String contactNumber;
	
	@Column(name = "paymentmethod")
	private String paymentMethod;
	
	@Column(name = "total")
	private String total;
	
	@Column(name="advocatedetails",columnDefinition="json")
	private String  advocateDetail;
	
	@Column(name="createdBy")
	private String createBy;

	public void setName(Object object) {
		// TODO Auto-generated method stub
		
	}

	public void setEmail(Object object) {
		// TODO Auto-generated method stub
		
	}

	public void setContactNumber(Object object) {
		// TODO Auto-generated method stub
		
	}

	public void setPaymentMethod(Object object) {
		// TODO Auto-generated method stub
		
	}

	public void setTotal(int parseInt) {
		// TODO Auto-generated method stub
		
	}

//	public void setUUId(int parseInt) {
//		// TODO Auto-generated method stub
//		
//	}

//	public void setUUId(int parseInt) {
//		// TODO Auto-generated method stub
//		
//	}

	public void setAdvocateDetail(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setCreatedBy(String currentUser) {
		// TODO Auto-generated method stub
		
	}

//	public void setUUId(String string) {
//		// TODO Auto-generated method stub
//		
//	}

	public void setUUId(String string) {
		// TODO Auto-generated method stub
		
	}
	

}
