package com.myShop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myShop.entities.User;
import com.myShop.repository.userRepo;

@Service
public class userService {
	
	@Autowired
	userRepo userRepo;
	
	
	public User saveUser(User user) {
		return userRepo.save(user);
	}
	
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}
	
	public User getUser (Long id) {
		return userRepo.findById(id).get();
	}
	
	public User findOne (String email) {
		Optional<User> user = userRepo.findByEmail(email); 
		//return userRepo.findByEmail(email).get();
		if (user.isPresent()) {
			return user.get(); 
		}
		return null;
		
	}
	
	
	public void deleteUser (Long id) {
		userRepo.deleteById(id);
	}

	public boolean isActive(String email) {
		User user = new User();
		user = findOne(email);
		if (user.getStatus().equals("active")) {
			return true;
		}
		return false;
	}
	
	/*
	public List<user> findUserSum() {
		return userRepo.findUserSum();
	}
	*/
}
