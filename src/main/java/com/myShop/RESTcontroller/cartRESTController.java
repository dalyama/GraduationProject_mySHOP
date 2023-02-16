package com.myShop.RESTcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myShop.services.cartService;
import com.myShop.services.productDetailsService;
import com.myShop.entities.cart;
import com.myShop.entities.productDetails;
import com.myShop.entities.User;

@RequestMapping("/api/cart/")
@RestController
public class cartRESTController {

	@Autowired
	cartService cartService;
	
	@Autowired
	productDetailsService productDetailsService;
	
	@GetMapping("/myCarts")
	public List<cart> getAllCarts (){
		return cartService.getAllCarts();
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "{id}/addCart")
	public cart saveCart(@PathVariable("id") Long id,@RequestParam("quantity") int quantity,HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		cart cart = new cart();
		productDetails productDetails = productDetailsService.getProductDetails(id);
		
		cart.setUser(user);
		cart.setProductDetails(productDetails);
		cart.setQuantity(quantity);
		if (user != null) {
			
			try {
				if (session.getAttribute("carts") == null) {

					return cartService.save(cart);
				}
				else {
					List<cart> listOfCarts = (List<cart>) session.getAttribute("carts");
					System.out.println("3");
					for (cart c : listOfCarts) {
						c.setUser(user);
					}
					listOfCarts.add(cart);
					System.out.println("4");
					
					cartService.saveAll(listOfCarts);
					session.removeAttribute("carts");
					return cart;
				}
			}catch(Exception e ) {
				System.out.println("cartRESTController.saveCart()");
			}
			
			return cart;
		}
		
		else {

			List<cart> listOfCarts = new ArrayList<cart>();
			System.out.println("1");
			try {
				if (session.getAttribute("carts") == null) {
					listOfCarts.add(cart);
					System.out.println("2");
				}
				else {
					listOfCarts = (List<cart>) session.getAttribute("carts");
					System.out.println("3");
					listOfCarts.add(cart);
					System.out.println("4");
				}
				session.setAttribute("carts", listOfCarts);
				System.out.println("User is : "+session.getId());
				System.out.println("Cart is Null / List of Carts size is : "+listOfCarts.size());
			}catch(Exception e ) {
				System.out.println("cartRESTController.saveCart()");
			}
			return cart;
		}
		
		
	}
	
	@GetMapping(value = "{id}/update/{quantity}")
	public ResponseEntity<Object> updateCart(
			@PathVariable("id") Long id , @PathVariable("quantity") int quantity) {
		
		cart cart = cartService.getCart(id);
		if (quantity < 1) {
			deleteCart(id);
		}
		else {
			cart.setQuantity(quantity);
			cartService.save(cart);
		}
		return new ResponseEntity<>("Success",HttpStatus.OK);
	}
	
	@GetMapping(value = "{id}/delete")
	public ResponseEntity<Object> deleteCart(@PathVariable("id") Long id){
		cartService.delete(id);
		return new ResponseEntity<>("Success",HttpStatus.OK);
	}
}
