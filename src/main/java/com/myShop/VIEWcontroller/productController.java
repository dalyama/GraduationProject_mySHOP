package com.myShop.VIEWcontroller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myShop.entities.category;
import com.myShop.entities.product;
import com.myShop.entities.productDetails;
import com.myShop.entities.productPhotos;
import com.myShop.entities.stock;
import com.myShop.entities.temporary.categoryGroup;
import com.myShop.repository.productPhotosRepo;
import com.myShop.services.categoryService;
import com.myShop.services.productDetailsService;
import com.myShop.services.productPhotosService;
import com.myShop.services.productService;
import com.myShop.services.stockService;
import com.myShop.services.storeService;

@Controller
@RequestMapping("/api/product")
public class productController {

	@Autowired
	productService productService;
	
	@Autowired
	categoryService categoryService;
	
	@Autowired
	productDetailsService productDetailsService;

	@Autowired
	storeService storeService;
	
	@Autowired
	stockService stockService;
	
	@Autowired
	productPhotosRepo productPhotosRepo;
	
	@Autowired
	productPhotosService productPhotosService;
	
	
	public static String uploadDirectory = "src/main/resources/static/images/uploads/productsDB";
	
	@GetMapping("/management")
	public String productManagement(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "product-management";
	}
	
	@GetMapping
	@RequestMapping(value = "/getAllProducts", produces = "application/json")
	public List<product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping
	@RequestMapping(path = { "getProduct/{id}" })
	public String getProduct(@PathVariable("id") Long id,Model model) {
		
		List<String> sizes = new ArrayList<String>();
		
		List<stock> stocks = stockService.findAllByProductDetails_Product_idAndCountGreaterThan(id,0);
		
		//List<stock> suggestedStocks = stockService.findTop30ByProductDetails_Product_Category_idAndCountGreaterThanOrderByProductDetails_idDesc(stocks.get(0).getProductDetails().getProduct().getCategory().getId(), 0);
		//List<product> suggestedStocks = productService.findTop30ByCategory_idAndTotalStockGreaterThanOrderByIdDesc(stocks.get(0).getProductDetails().getProduct().getCategory().getId(), 0);
		List<product> suggestedStocks = productService.findTop30ByCategory_idAndTotalStockGreaterThanAndIdNotOrderByIdDesc(stocks.get(0).getProductDetails().getProduct().getCategory().getId(), 0,id);
		/*
		 * Important Note ->
		 * Handling (size & colors) done by JS file <add-to-cart.js> & multi line in <product-info.html>
		 * */ 
		model.addAttribute("suggestedProducts", suggestedStocks);
		int count = 0;
		Long linkID = (long) 0;
		for (stock stock : stocks) {
			String size = stock.getProductDetails().getSize();
			if(!sizes.contains(size)) {
				sizes.add(size);
			}
		}
		
		List<String> colors = new ArrayList<String>();
		for(stock stock: stocks) {
			if (stock.getProductDetails().getSize().equals(sizes.get(0))) {
				String color = stock.getProductDetails().getColor();
				if(!colors.contains(color)) {
					colors.add(color);
					if(count == 0) {
						linkID = stock.getProductDetails().getId();
					}
					count++;
				}
			}
		}
		
		List<productPhotos> photos = productPhotosService.getAllByProduct_id(id);
		List<productDetails> productDetails = productDetailsService.getTopProductDetails(id);
		productDetails pd = new productDetails();
		pd = productDetails.get(0);
		model.addAttribute("sizes", sizes);
		model.addAttribute("colors",colors);
		model.addAttribute("id", linkID);
		model.addAttribute("stocks", stocks);
		model.addAttribute("productPhotoActive",pd);
		model.addAttribute("productPhotosId", photos);
		model.addAttribute("productPhotos", photos);
		model.addAttribute("productDetails", pd);
		
		//int quantity = 0;
		//model.addAttribute("quantity", quantity);
		return "product-info";
	}
	
	@GetMapping(value="/addProduct", produces = "application/json")
	public String addProductForm(Model model) {
		
		List<category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("product", new product());
		return "addProduct";
	}

	@PostMapping(value="/addProduct", produces = "application/json")
	public String saveProduct(@RequestParam("category.id") String cat_id, @RequestParam("name") String name,@RequestParam("madein") String madein,
			@RequestParam("gender") String gender,
			@RequestParam("brand") String brand,
			@RequestParam("albumImg") MultipartFile[] albumImg ,
			@RequestParam("files") MultipartFile[] files ) throws InterruptedException {
		product product = new product();
		Long category_id = Long.parseLong(cat_id);
		product.setCategory(categoryService.getCategory(category_id));
		product.setName(name);
		product.setMadein(madein);
		product.setGender(gender);
		product.setBrand(brand);
		StringBuilder fileNames = new StringBuilder();
		
		for(MultipartFile file : albumImg) {
			String fileName = getRandomString();
			fileName =  fileName+"-"+file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
			fileNames.append(file.getOriginalFilename());
			try {
				byte [] img=file.getBytes();
				product.setAlbumImg(img);
				product.setAlbumName(fileName);
				Files.write(fileNameAndPath, file.getBytes());
			}catch(Exception e) {
				System.err.println(e);
			}
		}
		

		List<productPhotos> productPhotosList = new ArrayList<productPhotos>();
		for(MultipartFile file : files) {
			if (!file.isEmpty()) {
				System.out.println("file part is not empty and them content is : "+file.getContentType());
				System.out.println("file part orginal name is : "+file.getOriginalFilename());
				productPhotos productPhotos = new productPhotos();
				String fileName = getRandomString();
				fileName =  fileName+"-"+file.getOriginalFilename();
				Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
				fileNames.append(file.getOriginalFilename());
				try {
					byte [] img =file.getBytes();
					productPhotos.setFiles(img);
					productPhotos.setName(fileName);
					productPhotos.setProduct(product);
					Files.write(fileNameAndPath, file.getBytes());
					productPhotosList.add(productPhotos);		
				}catch(Exception e) {
					System.err.println(e);
				}
			}
			
		}
		//product.setProductPhotos(productPhotosList);
		productService.saveProduct(product);
		if (!productPhotosList.isEmpty()) {
			productPhotosRepo.saveAll(productPhotosList);
		}
		Thread.sleep(4000);
		return "redirect:/api/product/addProductDetails/"+product.getId();
	}

	@PutMapping("/updateProduct")
	public product update(@RequestBody product product) {
		return productService.saveProduct(product);
	}

	@RequestMapping(path = { "deleteProduct/{id}" })
	public product delete(@PathVariable("id") Long id) {
		product product = productService.getProduct(id);
		productService.deleteProduct(id);
		return product;
	}
	
	@GetMapping("productDetails")
	public List<productDetails> getAllProductsDetails(){
		return productDetailsService.getAllProductsDetails();
	}
	
	
	@GetMapping(value = "addProductDetails/{id}")
	public String addProductDetailsForm(@PathVariable("id") Long id, Model model) {
		//model.addAttribute("productDetails", new productDetails());
		System.out.println("Saeed 123456789");
		product product = productService.getProduct(id);
		
		model.addAttribute("stock", new stock());
		model.addAttribute("product", product);
		model.addAttribute("stores", storeService.getAllStores());
		model.addAttribute("stocks", stockService.findAllByProduct_id(id));
		model.addAttribute("pid",id);
		return "addProductDetails";
	}
	
	/*
	@PostMapping("addProductDetails")
	public String addproductDetailsSave(@RequestParam("productDetails.product.id") Long pid,
			@RequestParam("store.id") Long sid,@RequestParam("productDetails.price") float price,
			@RequestParam("count") int count,
			@RequestParam("productDetails.color") String color,
			@RequestParam("productDetails.size") String size,
			@RequestParam("files") MultipartFile[] files , Model model) {
		
		product product = productService.getProduct(pid);
		store store = storeService.getStore(sid);
		productDetails productDetails = new productDetails();
		productDetails.setColor(color);
		productDetails.setPrice(price);
		productDetails.setSize(size);
		productDetails.setProduct(product);
		productDetails = productDetailsService.saveProductDetails(productDetails);
		List<productPhotos> productPhotosList = new ArrayList<productPhotos>();
		StringBuilder fileNames = new StringBuilder();
		for(MultipartFile file : files) {
			productPhotos productPhotos = new productPhotos();
			String fileName = getRandomString();
			fileName =  fileName+"-"+file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
			fileNames.append(file.getOriginalFilename());
			try {
				byte [] byteArr=file.getBytes();
				productPhotos.setFiles(byteArr);
				productPhotos.setName(fileName);
				Files.write(fileNameAndPath, file.getBytes());
				productPhotos.setProduct(product);
				productPhotosList.add(productPhotos);		
			}catch(Exception e) {
				System.err.println(e);
			}
		}
		productPhotosRepo.saveAll(productPhotosList);
		stock stock = new stock(store,productDetails);
		stock.setCount(count);
		stockService.saveStock(stock);
		model.addAttribute("msg","The product Details seved successfully.");
		//addProductDetailsForm(model);
		return "redirect:/api/product/addProductDetails";
		//return "addProductDetails";
	}
	*/
	
	
	@PostMapping(value= "addProductDetails/{pid}")
	public String addproductDetailsSave(@Valid stock stock,@PathVariable("pid") Long id ,Model model,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "redirect:/api/product/addProductDetails/"+id;
		}
		
		product product = productService.getProduct(id);
		if (product.getCost() > stock.getProductDetails().getPrice() || product.getCost() < 0.1) {
			product.setCost(stock.getProductDetails().getPrice());
		}
		stock.getProductDetails().setProduct(product);
		
		System.out.println(stock.getProductDetails().getProduct().getId());
		System.out.println("Product Name is addproductDetailsSave : "+stock.getProductDetails().getProduct().getName());
		
		product.setTotalStock(product.getTotalStock()+stock.getCount());
		productDetailsService.saveProductDetails(stock.getProductDetails());
		stockService.saveStock(stock);
		System.out.println("finished ..............");
		//addProductDetailsForm(id,model);
		return "redirect:/api/product/addProductDetails/"+id;
	}
	
	@GetMapping("productsTable")
	public String productTable(Model model) {
		List<product> products = productService.getAllProductsOrderByDesc();
		model.addAttribute("products", products);
		return "productsTable";
	}
	
	
	@GetMapping("/search")
	public String search(@RequestParam(defaultValue = "null") String search,
			@RequestParam(defaultValue = "1") int page
			,Model model) {
		Page <product > listProducts;
		if (search.equals("null")) {
			listProducts = productService.findByTotalStockGreaterThan(0, PageRequest.of(page - 1, 20));
		}
		else {
			listProducts = productService.findAllByName(search,PageRequest.of(page - 1,20));
		}
		
		model.addAttribute("products", listProducts);
		model.addAttribute("currentPage", page);
		model.addAttribute("categories", categoryService.getAllCategories());
		return "index";
	}
	
	
	@PostMapping("/search")
	public String searchProduct(@RequestParam("search") String search,
			@RequestParam(defaultValue = "1") int page
			,Model model) throws UnsupportedEncodingException {

		return "redirect:/home?name="+ URLEncoder.encode(search, "UTF-8");
		/*
		Page <product > listProducts;
		if (search.isEmpty()) {
			listProducts = productService.findAllByTotalStockGreaterThanOrderByIdDesc(0, PageRequest.of(page - 1, 20));
		}
		else {
			listProducts = productService.findAllByName(search,PageRequest.of(page - 1,20));
		}
		
		model.addAttribute("products", listProducts);
		model.addAttribute("currentPage", page);
		model.addAttribute("categories", categoryService.getAllCategories());
		return "index";
		*/
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*****/
	
	public String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String Key = salt.toString();
        return Key;
	}
	
	
	@GetMapping("groubBy")
	public void productGroup() {
		try {
			
			for (categoryGroup categoryGroup : productService.groupBy()) {
				//System.out.println("Category Id: " + categoryGroup.getCategoryId());
				System.out.println("Category Name: " + categoryGroup.getCategoryName());
				System.out.println("Min Price: " + categoryGroup.getMinPrice());
				System.out.println("Max Price: " + categoryGroup.getMaxPrice());
				System.out.println("Sum Quantity: " + categoryGroup.getSumQuantity());
				System.out.println("Count Product: " + categoryGroup.getCountProduct());
				System.out.println("Avg Price: " + categoryGroup.getAvgPrice());
				System.out.println("==================");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
    
}
