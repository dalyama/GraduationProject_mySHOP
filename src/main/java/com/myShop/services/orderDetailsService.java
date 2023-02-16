package com.myShop.services;
import java.util.List;

import com.myShop.entities.orderDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myShop.repository.orderDetailsRepo;

@Service
public class orderDetailsService {

	@Autowired
	orderDetailsRepo orderDetailsRepo;
	
	public List<orderDetails> getAllOrdersDetails(){
		return orderDetailsRepo.findAll();
	}
	
	public List<orderDetails> getAllOrdersDetailsByOrderId(Long order_id){
		return orderDetailsRepo.getAllOrdersDetailsByOrderId(order_id);
	}

	public int getCountOrdersDetailsByOrderId(Long order_id) {
		return orderDetailsRepo.getCountOrdersDetailsByOrderId(order_id);
	}
	
	
}
