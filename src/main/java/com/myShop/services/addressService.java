package com.myShop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myShop.entities.address;
import com.myShop.repository.addressRepo;

@Service
public class addressService {

	@Autowired
	addressRepo addressRepo;
	
	public void save (address address) {
		addressRepo.save(address);
	}
	
	public List<address> getAll() {
		return addressRepo.findAll();
	}

	public address findOneByUser_Email(String email) {
		// TODO Auto-generated method stub
		return addressRepo.findOneByUser_Email(email);
	}
}
