package com.onlineadvocatebooking.POJO;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;



@NamedQuery(name = "Product.getAllProduct",query="select new com.onlineadvocatebooking.wrapper.ProductWrapper(p.id,p.name,p.description,p.rent,p.status,p.category.id,p.category.name) from Product p ")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="product")

public class Product implements Serializable {
	private static final long serialVersionUID = 123456L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
  @Column(name="name")
	private String name;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="category_fk",nullable=false)
  private Category category;
 
  @Column(name="description")
  private String description;
  
  @Column(name="rate")
  private Integer rate;
  
  @Column(name="status")
  private String status;

public void setId(int parseInt) {
	// TODO Auto-generated method stub
	
}

public void setStatus(String string) {
	// TODO Auto-generated method stub
	
}

public void setCategory(Category category2) {
	// TODO Auto-generated method stub
	
}

public void setrent(int parseInt) {
	// TODO Auto-generated method stub
	
}

public void setName(String string) {
	// TODO Auto-generated method stub
	
}

public void setDescription(String string) {
	// TODO Auto-generated method stub
	
}

public String getStatus() {
	// TODO Auto-generated method stub
	return null;
}

}
