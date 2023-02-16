package com.myShop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myShop.repository.categoryRepo;
import com.myShop.entities.category;

@Service
public class categoryService {

	@Autowired
	categoryRepo categoryRepo;
	
	public List<category> getAllCategories() {
		return categoryRepo.findAll();
	}
	
	public category getCategory(Long id) {
		return categoryRepo.findById(id).get();
	}
	
	public category saveCategory(category category) {
		return categoryRepo.save(category);
	}
	
	public void deleteCategory(Long id) {
		 categoryRepo.deleteById(id);
	}
}
