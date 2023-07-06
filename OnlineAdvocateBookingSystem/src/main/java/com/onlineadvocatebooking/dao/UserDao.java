package com.onlineadvocatebooking.dao;
import com.onlineadvocatebooking.POJO.User;
import com.onlineadvocatebooking.wrapper.UserWrapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends JpaRepository<User , Integer> {
	User findByEmailId(@Param("email") String email);
	
	List<UserWrapper>getAllUser();
	List<String> getAllAdmin();
	
	
	@Transactional
	@Modifying
	Integer updateStatus(@Param("status")String status,@Param("id")Integer id);

	void updateStatus(Object object, int parseInt);
	
	User findByEmail(Object object);

}
