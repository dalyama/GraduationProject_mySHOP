package com.myShop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myShop.entities.productDetails;
import com.myShop.repository.productDetailsRepo;

@Service
public class productDetailsService {

	@Autowired
	productDetailsRepo productDetailsRepo;
	
	public List<productDetails> getAllProductsDetails(){
		return productDetailsRepo.findAll();
	}
	
	public productDetails saveProductDetails(productDetails productDetails) {
		return productDetailsRepo.save(productDetails);
	}
	
	public productDetails getProductDetails(Long id) {
		return productDetailsRepo.findById(id).get();
	}

	public List<productDetails> getProductsDetails(Long product_id) {
		return productDetailsRepo.findAllById(product_id);
	}

	public void deleteProductDetails(Long id) {
		productDetailsRepo.deleteById(id);
	}
	 public List<productDetails> getTopProductDetails(Long product_id) {
		 return productDetailsRepo.findAllByProduct_id(product_id);
	 }
}

