package com.myShop.RESTcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myShop.entities.address;
import com.myShop.entities.category;
import com.myShop.entities.product;
import com.myShop.entities.productDetails;
import com.myShop.entities.productPhotos;
import com.myShop.entities.stock;
import com.myShop.entities.store;
import com.myShop.entities.User;
import com.myShop.services.productDetailsService;
import com.myShop.services.productPhotosService;
import com.myShop.services.productService;
import com.myShop.services.stockService;
import com.myShop.services.storeService;
import com.myShop.services.userService;
import com.myShop.services.addressService;
import com.myShop.services.categoryService;

@RequestMapping("/test")
@RestController
public class testController {
	

	@Autowired
	storeService storeService;
	
	@Autowired
	categoryService categoryService;
	
	@GetMapping("/stores")
	public List<store> getAllStores(){
		return storeService.getAllStores();
	}
	
	@Autowired
	productService productService;
	
	
	@GetMapping("products")
	public List<product> getAllProducts(){
		return productService.getAllProducts();
	}
	@GetMapping("product")
	public product saveProduct() {
		int i = 1;
		Long id = Long.valueOf(i);
		category category = categoryService.getCategory(id);
		System.out.println("Category id is : "+category.getId()+" name is : "+category.getName());
		productDetails productDetails = productDetailsService.getProductDetails(id);
		System.out.println("PrpoductDetails id is : "+productDetails.getId()+" name is : "+productDetails.getColor());
		product product = new product("product name","product madein","product desc", category);
		return productService.saveProduct(product);
	}
	@Autowired
	productDetailsService productDetailsService;
	
	@GetMapping("productsDetails")
	public List<productDetails> productsDetails(){
		return productDetailsService.getAllProductsDetails();
	}
	
	@Autowired
	stockService stockService;
	
	@GetMapping("stocks")
	public List<stock> getAllStocks(){
		return stockService.getAllStocks();
	}
	
	@GetMapping("stock")
	public stock saveStock() {
		Long pid = (long) 1;
		Long sid = (long) 1;
		productDetails p = productDetailsService.getProductDetails(pid);
		store s = storeService.getStore(sid);
		stock stock = new stock(s,p);
		return stockService.saveStock(stock);
	}
	
	
	@Autowired
	productPhotosService productPhotosService;
	
	@GetMapping("productPhotos")
	public List<productPhotos> getAll(){
		return productPhotosService.getAll();
	}
	
	
	@Autowired
	userService userService;
	
	@GetMapping("users")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("autocomplate")
	public String autoComplete() {
		return "AutoComplete";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	@ResponseBody
	public List<String> search(HttpServletRequest request) {
		System.out.println("/test/search");
		return productService.search(request.getParameter("term"));
	}
	
	@Autowired
	addressService addressService;
	@GetMapping("/address")
	public List<address> getAllAdresses(){
		return addressService.getAll();
	}
	
	
	public void display() {
		System.out.println("testController.display()");
	}
}
