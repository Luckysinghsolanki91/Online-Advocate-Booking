package com.onlineadvocatebooking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;

import com.onlineadvocatebooking.POJO.Category;

public interface CategoryDao extends JpaRepository<Category,Integer> {
	List<Category> getAllCategory();
	

}
