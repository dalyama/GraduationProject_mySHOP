package com.myShop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myShop.entities.store;
import com.myShop.repository.storeRepo;

@Service
public class storeService {

	@Autowired 
	storeRepo storeRepo;
	
	public store saveStore(store store) {
		return storeRepo.save(store);
	}
	
	public List<store> getAllStores(){
		return storeRepo.findAll();
	}
	
	public store getStore (Long id) {
		return storeRepo.findById(id).get();
	}
	
	public void deleteStore (Long id) {
		storeRepo.deleteById(id);
	}
}
