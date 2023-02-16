package com.myShop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myShop.entities.cart;
import com.myShop.repository.cartRepo;

@Service
public class cartService {

	@Autowired
	cartRepo cartRepo;
	
	public cart getCart(Long id) {
		return cartRepo.findById(id).get();
	}
	
	public List<cart> getAllCarts(){
		return cartRepo.findAll();
	}
	
	public cart save(cart cart) {
		return cartRepo.save(cart);
	}
	
	public void delete(cart cart) {
		 cartRepo.delete(cart);
	}
	
	public void delete(Long id) {
		cartRepo.deleteById(id);
	}

	public List<cart> saveAll(List<cart> listOfCarts) {
		return cartRepo.saveAll(listOfCarts);
	}
	
	public List<cart> findAllByUser_id(Long user_id){
		return cartRepo.findAllByUser_id(user_id);
	}
}
