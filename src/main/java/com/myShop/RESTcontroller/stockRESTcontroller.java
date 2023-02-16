package com.myShop.RESTcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.myShop.services.stockService;

import com.myShop.entities.stock;

@RequestMapping("api/stock")
@RestController
public class stockRESTcontroller {
	
	@Autowired
	stockService stockService;
	
	stock stock = new stock();
	
	@GetMapping("/")
	public List<stock> getAllStocks() {
		return stockService.getAllStocks();
	}
	
	
	@GetMapping(value= "/{id}/allAvailableStock")
	public List<stock> getAllAvailableStock(@PathVariable("id") Long id ){
		return stockService.findAllByProductDetails_Product_idAndCountGreaterThan(id, 0);
	}
}
