package com.myShop.VIEWcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myShop.entities.order;
import com.myShop.entities.orderDetails;
import com.myShop.services.orderDetailsService;
import com.myShop.services.orderService;;

@RequestMapping("/api/order")
@RestController
public class orderController {

	@Autowired
	orderService orderService;

	@Autowired
	orderDetailsService orderDetailsService;

	@GetMapping
	@RequestMapping(value = "/getAllOrders", produces = "application/json")
	public List<order> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping
	@RequestMapping(path = { "/getOrder/{id}" })
	public order getOrder(@PathVariable("id") Long id) {
		return orderService.getOrder(id);
	}

	@PostMapping
	@RequestMapping(value = "/saveOrder")
	public order saveProduct(@RequestBody order order) {
		return orderService.saveOrder(order);

	}

	@PutMapping("/updateOrder")
	public order update(@RequestBody order order) {
		return orderService.saveOrder(order);

	}

	@RequestMapping(path = { "/deleteOrder/{id}" })
	public order delete(@PathVariable("id") Long id) {
		order order = orderService.getOrder(id);
		orderService.deleteOrder(id);
		return order;
	}

	/*
	 * 
	 * 
	 * 
	 * */
	// get all details for all orders
	@GetMapping
	@RequestMapping(value = "/getAllOrdersDetails", produces = "application/json")
	public List<orderDetails> getAllOrdersDetails() {
		return orderDetailsService.getAllOrdersDetails();
	}

	// get all details for one order
	@GetMapping
	@RequestMapping(path = { "/getOrder/{id}/getAllDetails" })
	public List<orderDetails> getAllOrdersDetailsByOrderId(@PathVariable("id") Long order_id) {
		return orderDetailsService.getAllOrdersDetailsByOrderId(order_id);
	}

	// get all details for one order
	@GetMapping
	@RequestMapping(path = { "/getOrder/{id}/getAllDetails/count" })
	public int getCountOrdersDetailsByOrderId(@PathVariable("order_id") Long order_id) {
		return orderDetailsService.getCountOrdersDetailsByOrderId(order_id);
	}

}
