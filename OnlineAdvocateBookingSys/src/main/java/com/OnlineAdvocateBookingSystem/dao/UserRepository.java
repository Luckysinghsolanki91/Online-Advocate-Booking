package com.OnlineAdvocateBookingSystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OnlineAdvocateBookingSystem.entities.User;
public interface UserRepository extends JpaRepository<User,Integer>{

}
