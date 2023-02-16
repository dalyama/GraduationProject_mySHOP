package com.myShop.RESTcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myShop.services.productDetailsService;
import com.myShop.services.productPhotosService;
import com.myShop.services.productService;



import com.myShop.entities.product;
import com.myShop.entities.productDetails;
import com.myShop.entities.productPhotos;

@RequestMapping("/api")
@RestController
public class productRESTcontroller {

	@Autowired
	productService productService;
	@GetMapping("product")
	public List<product> getAllproducts(){
		return productService.getAllProducts();
	}
	/*
	@GetMapping("product/available")
	public List<product> getAllAvailableProducts() {
		return productService.getAllAvailableProducts();
	}
	*/
	@GetMapping(value = "product/{id}")
	public product getProduct(@PathVariable("id") Long id ) {
		return productService.getProduct(id);
	}
	
	@PostMapping("product")
	public product saveProduct(product product) {
		return productService.saveProduct(product);
	}
	
	@PutMapping("product")
	public product updateProduct(product product) {
		return productService.saveProduct(product);
	}
	
	@DeleteMapping(value = "product/{id}")
	public void deleteProduct(@PathVariable("id") Long id) {
		productService.deleteProduct(id);
	}
	/*** End Product ***/
	/*** Start ProductDetails ***/
	@Autowired
	productDetailsService productDetailsService;
	
	@GetMapping(value = "product/{id}/productDetails")
	public List<productDetails> getProductDetails(@PathVariable("id") Long id){
		return productDetailsService.getProductsDetails(id);
	}
	
	@GetMapping(value = "product/{id}/productDetails/{pid}")
	public productDetails getProductDetails(@PathVariable("id") Long id , @PathVariable("pid") Long pid) {
		return productDetailsService.getProductDetails(pid);
	}
	
	@PostMapping(value = "product/{id}/productDetails")
	public productDetails saveProductDetails(@PathVariable("id") Long id,productDetails productDetails) {
		productDetails.setProduct(productService.getProduct(id));
		return productDetailsService.saveProductDetails(productDetails);
	}
	
	@PutMapping(value = "product/{id}/productDetails")
	public productDetails updateProductDetails(@PathVariable("id") Long id,productDetails productDetails) {
		productDetails.setProduct(productService.getProduct(id));
		return productDetailsService.saveProductDetails(productDetails);
	}
	
	@DeleteMapping(value = "product/{id}/productDetails/{pid}")
	public void deleteProductDetails (@PathVariable("id") Long id , @PathVariable("pid") Long pid) {
		productDetailsService.deleteProductDetails(pid);
	}
	
	/* get all productDetails for one category */
	/*
	@GetMapping(value = "category/{id}/productDetails")
	public List<productDetails> getAllProductDetailsForOneCategory(@PathVariable("id") Long id){
		return productService.getAllProductDetailsForOneCategory(id);
	}
	*/
	/*** End ProductDetails ***/
	/*** Start ProductPhotos ***/
	
	@Autowired
	productPhotosService productPhotosService;
	
	@GetMapping(value = "product/{id}/productPhotos")
	public List<productPhotos> getProductPhotos(@PathVariable("id") Long id){
		return productPhotosService.getAllByProduct_id(id);
	}
	
	
	
	@PostMapping(value = "product/{id}/productDetails/{pid}/productPhotos")
	public productPhotos saveProductPhotos(productPhotos productPhotos) {
		return productPhotosService.saveProductPhotos(productPhotos);
	}
	
	@PutMapping(value = "product/{id}/productDetails/{pid}/productPhotos")
	public productPhotos updateProductPhotos(productPhotos productPhotos) {
		return productPhotosService.saveProductPhotos(productPhotos);
	}
	
	@DeleteMapping(value = "product/{id}/productDetails/{pid}/productPhotos/{ppid}")
	public void deleteProductPhotos(@PathVariable("id") Long id , @PathVariable("pid") Long pid , @PathVariable("ppid") Long ppid) {
		productPhotosService.deleteProductPhotos(ppid);
	}
	
	@DeleteMapping(value = "product/{id}/productDetails/{pid}/productPhotos")
	public void deleteProductDetailsPhotos(@PathVariable("id") Long id,@PathVariable("pid")Long pid) {
		productPhotosService.deleteProductDetailsPhotos(pid);
	}

	
	
	
}
