package com.myShop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myShop.entities.stock;
import com.myShop.repository.stockRepo;

import java.util.List;


@Service
public class stockService {
	@Autowired
	stockRepo stockRepo;
	
	public List<stock> getAllStocks(){
		return stockRepo.findAll();
	}
	
	public stock saveStock(stock stock) {
		return stockRepo.save(stock);
	}
	
	public List<stock> findAllByProduct_id(Long product_id){
		return stockRepo.findAllByProductDetails_Product_id(product_id);
	}
	
	
	public List<stock> findAllByProductDetails_Product_idAndCountGreaterThan(Long product_id,int count){
		return stockRepo.findAllByProductDetails_Product_idAndCountGreaterThanOrderByProductDetails_idDesc(product_id,count);
	}
	
	public List<stock> findTop30ByProductDetails_Product_Category_idAndCountGreaterThanOrderByProductDetails_idDesc(Long category_id,int count){
		return stockRepo.findTop30ByProductDetails_Product_Category_idAndCountGreaterThanOrderByProductDetails_idDesc(category_id, 0);
	}

}
