package com.myShop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myShop.entities.User;

@Repository
public interface userRepo extends JpaRepository<User, Long> {


	//public User findByEmail(String email);
	
	// for userDetailsService
	public Optional<User> findByEmail(String email);
	
	//@Query("SELECT new com.myShop.entities.user(u.country, SUM(id)) FROM user u GROUP BY u.country")
	//List<user> findUserSum();
	

}
