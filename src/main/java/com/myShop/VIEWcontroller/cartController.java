package com.myShop.VIEWcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myShop.entities.cart;
import com.myShop.entities.productDetails;
import com.myShop.entities.response;
import com.myShop.entities.User;
import com.myShop.services.cartService;
import com.myShop.services.productDetailsService;
import com.myShop.services.userService;

@RequestMapping("/cart")
@Controller
public class cartController {
	@Autowired
	cartService cartService;

	@Autowired
	userService userService;

	@Autowired
	productDetailsService productDetailsService;

	@SuppressWarnings("unchecked")
	@GetMapping("")
	public String myCarts(Model model, HttpSession session) {

		System.out.println("cartController.myCarts() 100");
		User user = new User();
		List<cart> carts = new ArrayList<cart>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("cartController.myCarts() 123");
		if (!(auth.getName().equals("anonymousUser"))) {
			System.out.println("cartController.myCarts() 111");
			System.out.println("auth name is : "+auth.getName());
			String email = auth.getName().toString();
			user = userService.findOne(email);
			carts = cartService.findAllByUser_id(user.getId());
			if(session.getAttribute("carts") != null) {
				List<cart> sessionCarts = (List<cart>) session.getAttribute("carts");
				sessionCarts = saveSession(sessionCarts , user);
				carts.addAll(sessionCarts);
				session.removeAttribute("carts");
			}
		}
		else if (session.getAttribute("carts") != null) {
			carts = (List<cart>) session.getAttribute("carts");
		}

		System.out.println("cart list size : " + carts.size());
		int totalQuantity = 0;
		float totalPrice = 0;

		for (cart cart : carts) {
			totalQuantity = totalQuantity + cart.getQuantity();
			totalPrice = totalPrice + (cart.getQuantity() * cart.getProductDetails().getPrice());
		}
		model.addAttribute("carts", carts);
		model.addAttribute("totalQuantity", totalQuantity);
		model.addAttribute("totalPrice", totalPrice);
		return "myCarts";
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "{id}/addCart")
	public String saveCart(@PathVariable("id") Long id, @RequestParam("quantity") int quantity,HttpSession session) {
		System.out.println("cart product id : "+id);
		if (quantity < 1) {
			return "redirect:/api/product/getProduct/"+id;
		}
		User user = (User) session.getAttribute("user");

		cart cart = new cart();
		productDetails productDetails = productDetailsService.getProductDetails(id);

		cart.setUser(user);
		cart.setProductDetails(productDetails);
		cart.setQuantity(quantity);

		if (user != null) {
			try {
				if (session.getAttribute("carts") == null) {
					cartService.save(cart);
					return "redirect:/cart";
				} else {
					List<cart> listOfCarts = (List<cart>) session.getAttribute("carts");
					for (cart c : listOfCarts) {
						c.setUser(user);
					}
					listOfCarts.add(cart);
					cartService.saveAll(listOfCarts);
					session.removeAttribute("carts");
					return "redirect:/cart";
				}
			} catch (Exception e) {
				System.out.println("cartRESTController.saveCart()");
			}
			return "redirect:/cart";
		}

		else {

			List<cart> listOfCarts = new ArrayList<cart>();
			try {
				if (session.getAttribute("carts") == null) {
					listOfCarts.add(cart);
				} else {
					listOfCarts = (List<cart>) session.getAttribute("carts");
					listOfCarts.add(cart);
				}
				session.setAttribute("carts", listOfCarts);
			} catch (Exception e) {
				System.out.println("cartRESTController.saveCart()");
			}
			return "redirect:/cart";
		}

	}
	
	public List<cart> saveSession(List<cart> carts,User user) {
		for (cart c : carts) {
			c.setUser(user);
		}
		return cartService.saveAll(carts);
	}
	

	@RequestMapping(value = "{id}/update/{quantity}", method = RequestMethod.GET)
	@ResponseBody
	public response updateCart(@PathVariable("id") Long id, @PathVariable("quantity") int quantity) {

		response response = new response();
		cart cart = cartService.getCart(id);
		if (quantity < 1) {
			deleteCart(id);
		} else {
			cart.setQuantity(quantity);
			cartService.save(cart);
		}
		response.setData(cart);
		response.setStatus("done");
		return response;
	}

	@GetMapping(value = "{id}/delete")
	@ResponseBody
	public response deleteCart(@PathVariable("id") Long id) {
		response response = new response();
		cartService.delete(id);
		response.setData("deleted");
		response.setStatus("done");
		return response;
	}
}
