package com.onlineadvocatebooking.wrapper;

import lombok.Data;

@Data
public class ProductWrapper  {
  Integer id;
  String name;
  String description;
  Integer rent;
  String status;
  Integer categoryId;
  String categoryName;
  //default constructor
  public ProductWrapper(){
	  
  }
  public ProductWrapper(Integer id,String name ,String description , Integer rent, String status , Integer categoryId, String categoryName) {
	  this.id=id;
	  this.name=name;
	  this.description=description;
	  this.rent=rent;
	  this.status=status;
	  this.categoryId=categoryId;
	  this.categoryName=categoryName;
	  
  }
}
