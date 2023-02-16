package com.myShop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myShop.entities.productPhotos;
import com.myShop.repository.productPhotosRepo;

@Service
public class productPhotosService {
	
	@Autowired
	productPhotosRepo productPhotosRepo;
	
	
	public List<productPhotos> getAll(){
		return productPhotosRepo.findAll();
	}


	public List<productPhotos> getAllByProduct_id(Long id) {
		return productPhotosRepo.findAllByProduct_id(id);
	}


	public List<productPhotos> saveAll(List<productPhotos> list) {
		return productPhotosRepo.saveAll(list);
	}
	public productPhotos saveProductPhotos(productPhotos productPhotos) {
		return productPhotosRepo.save(productPhotos);
	}

	//delete all images related for one productDetails 
	public void deleteProductDetailsPhotos(Long id) {
		productPhotosRepo.deleteAllByProduct_id(id);
	}

	//delete one image by id
	public void deleteProductPhotos(Long id) {
		productPhotosRepo.deleteById(id);
	}
	
}
