package com.myShop.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myShop.entities.category;
import com.myShop.entities.User;
import com.myShop.services.categoryService;
import com.myShop.services.userService;


@Controller
@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
@RequestMapping("/admin/dashboard")
public class dashboardController {
	@Autowired
	userService userService;
	
	@GetMapping("")
	public String dashboard() {
		return "redirect:/admin/dashboard/totalReports";
	}
	
	@GetMapping("/totalReports")
	public String totalReports() {
		return "totalReports";
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		model.addAttribute("users",userService.getAllUsers());
		return "index";
	}
	
	@GetMapping("users/{id}")
	//@RequestMapping(path = { "users/{id}" } , method = RequestMethod.GET)
	public String getUser(@PathVariable("id") Long id , Model model) {
		List<User> users = new ArrayList<User>();
		users.add(userService.getUser(id));
		model.addAttribute("users",users);
		
		//model.addAttribute("user",userService.findOne(email));
		return "table";
	}
	
	@Autowired
	categoryService categoryService;
	
	
	@GetMapping("/management")
	public String categoryPanel(Model model) {
		model.addAttribute("category", new category());
		model.addAttribute("categories", categoryService.getAllCategories());
		return "category-management";
	}
	
	/*
	@GetMapping("")
	public String searchUsers(Model model , @RequestParam(defaultValue = "") String name) {
		model.addAttribute("users", userService.findByName(name));
		return "";
	}
	*/
}
