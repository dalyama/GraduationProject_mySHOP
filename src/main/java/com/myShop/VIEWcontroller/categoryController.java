package com.myShop.VIEWcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myShop.services.categoryService;
import com.myShop.entities.category;
import com.myShop.repository.categoryRepo;

@RequestMapping("/api/category")
@Controller
public class categoryController {

	@Autowired
	categoryService categoryService;
	
	@Autowired
	categoryRepo categoryRepo;
	
	@GetMapping("/management")
	public String categoryPanel(Model model) {
		model.addAttribute("category", new category());
		model.addAttribute("categories", getAllCategories());
		return "category-management";
	}
	
	@RequestMapping("/getAllCategories")
	public List<category> getAllCategories(){
		return categoryService.getAllCategories();
	}
	
	@GetMapping
	@RequestMapping(path = { "getCategory/{id}" } )
	public category getCategory(@PathVariable("id") Long id) {
		return categoryService.getCategory(id);
	}
	
	@GetMapping(value = "/update/{id}")
	public String addCategory(@PathVariable("id") Long id , Model model) {
		//model.addAttribute("category", new category());
		model.addAttribute("category", categoryService.getCategory(id));
		return "addCategory";
	}
	
	@PostMapping(value = "/update/{id}")
	public String updateCategory(category category) {
		//category.setId(id);
		categoryService.saveCategory(category);
		return "redirect:/api/category/management";
	}
	
	@PostMapping("/addCategory")
	public String addCategory(@Valid category category) {
		 categoryService.saveCategory(category);
		 return "redirect:/api/category/management";
	}
	
	@GetMapping(value = "/delete/{id}")
	public String deleteCategory (@PathVariable("id") Long id) {
		categoryRepo.deleteById(id);
		return "redirect:/api/category/management";
	}
	
	
	
}
