package com.myShop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myShop.entities.product;
import com.myShop.entities.temporary.categoryGroup;
import com.myShop.repository.productRepo;

@Service
public class productService {

	@Autowired 
	productRepo productRepo;
	
	public product saveProduct(product product) {
		return productRepo.save(product);
	}
	
	public List<product> getAllProducts(){
		return productRepo.findAll();
	}
	
	public product getProduct (Long id) {
		return productRepo.findById(id).get();
	}
	
	public void deleteProduct (Long id) {
		productRepo.deleteById(id);
	}
	/*
	public List<Object> getAllProductWithMinimum(){
		return productRepo.getAllProductWithMinimum();
	}

	public List<product> getAllAvailableProducts() {
		
		return productRepo.getAllAvailableProducts();
	}
	*/
	/*
	public List<productDetails> getAllProductDetailsForOneCategory(Long id) {
		return productRepo.getAllProductDetailsForOneCategory(id);
	}
	*/

	public List<product> getAllProductsOrderByDesc() {
		return productRepo.findTop10ByOrderByIdDesc();
	}
	
	

	public List<String> search(String keyword) {
		return productRepo.search(keyword);
	}
	
	
	public List<categoryGroup> groupBy(){
		return productRepo.groupBy();
	}

	public List<product> findTop30ByCategory_idAndTotalStockGreaterThanOrderByIdDesc(Long id , int count){
		return productRepo.findTop30ByCategory_idAndTotalStockGreaterThanOrderByIdDesc(id, 0);
	}

	public List<product> findTop30ByCategory_idAndTotalStockGreaterThanAndIdNotOrderByIdDesc(
			Long cid, int i, Long id) {
		// TODO Auto-generated method stub
		return productRepo.findTop30ByCategory_idAndTotalStockGreaterThanAndIdNotOrderByIdDesc(cid , i , id);
	}

	public List<product> findAllByTotalStockGreaterThanOrderByIdDesc(int totalStock) {
		return productRepo.findAllByTotalStockGreaterThanOrderByIdDesc(totalStock);
	}

	public Page<product> findAllByTotalStockGreaterThanOrderByIdDesc(int totalStock, Pageable pageable) {
		return productRepo.findAllByTotalStockGreaterThanOrderByIdDesc(totalStock ,pageable);
	}
	
	public Page<product> findByTotalStockGreaterThan(int totalStock, Pageable pageable) {
		return productRepo.findByTotalStockGreaterThan(totalStock ,pageable);
	}
	
	public Page<product> findAllByName(String search, Pageable pageable) {
		return productRepo.findByNameLike("%"+search+"%" ,pageable);
	}
	
	
	
	
	
}
