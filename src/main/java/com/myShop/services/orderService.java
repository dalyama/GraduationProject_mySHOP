package com.myShop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myShop.entities.order;
import com.myShop.repository.orderRepo;

@Service
public class orderService {

	@Autowired
	orderRepo orderRepo;
	
	public order saveOrder(order orders) {
		return orderRepo.save(orders);
	}
	
	public List<order> getAllOrders(){
		return orderRepo.findAll();
	}
	
	public order getOrder (Long id) {
		return orderRepo.findById(id).get();
	}
	
	public void deleteOrder (Long id) {
		orderRepo.deleteById(id);
	}
}
