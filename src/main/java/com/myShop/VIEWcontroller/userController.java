package com.myShop.VIEWcontroller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myShop.entities.address;
import com.myShop.entities.MailRequest;
import com.myShop.entities.Roles;
import com.myShop.entities.User;
import com.myShop.repository.userRepo;
import com.myShop.services.addressService;
import com.myShop.services.productService;
import com.myShop.services.userService;

@RequestMapping("/api/users")
@Controller
public class userController {

	@Autowired
	userService userService;

	@Autowired
	addressService addressService;
	
	@Autowired
	productService productService;
	
	@Autowired
	MyMailSenderController myMailSenderController;
	
	@GetMapping("panel")
	public String getAllUsers(Model model) {
		System.out.println("Get all users...");
		List <User> users = userService.getAllUsers();
		model.addAttribute("users",users);
		System.out.println("Came back from userService.getAllUsers().");
		return "user-management";
	}
	
	@GetMapping
	@RequestMapping(path = { "/{id}" } )
	public User getUser(@PathVariable("id") Long id) {
		return userService.getUser(id);
	}
	
	
	
	@RequestMapping(path= {"/register"},method = RequestMethod.GET)
	public String registerForm(Model model) {
		model.addAttribute("user",new User());
		System.out.println("Registeration Form, RequestMapping , Get Method");
		return "register";
	}
	
	@Autowired
	userRepo userRepo;
	@PostMapping("/register")
	public String saveUser(@Valid User user , BindingResult bindingResult , Model model) {
		
		if (userService.findOne(user.getEmail()) != null) {
			model.addAttribute("exist",true);
			return "register";
		}
		
		if (bindingResult.hasErrors()) {
			return "register";
		}
		
		else if (!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("passwordNotMatch", true);
			return "register";
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		System.out.println("userController.saveUser()");
		User newUser = userService.saveUser(user);
		if(newUser != null) {
			Roles role = new Roles();
			role.setId(3);
			role.setRole("USER");
			Set<Roles> roles = new HashSet<Roles>();
			roles.add(role);
			user.setRoles(roles);
			userService.saveUser(newUser);
		}
		
		//Send Verifying code
		MailRequest mailRequest = new MailRequest();
		mailRequest.setSubject("Actvation Code");
		mailRequest.setContent(newUser.getStatus());
		mailRequest.setTo(newUser.getEmail());
		mailRequest.setName(newUser.getName());
		myMailSenderController.sendEmail(mailRequest);
		
		
		return "redirect:/account/activation?email="+newUser.getEmail();
		//return "redirect:/api/users/"+user.getId()+"/address";
		//return "redirect:/myAccount";
		
	}
	
	
	@PutMapping("")
	public User update(@RequestBody User user) {
		return  userService.saveUser(user);
		
	}
	
	//@DeleteMapping
	// i don't know why i can't use @DeleteMapping , when i used it i had error 
	@RequestMapping(path = { "users/{id}" },method = RequestMethod.DELETE )
	public User delete(@PathVariable("id") Long id) {
		User user = userService.getUser(id);
		userService.deleteUser(id);
		return user;
	}
	
	/*
	 * All operations related to the address
	 */
	
	@RequestMapping(path= { "{id}/address" } , method = RequestMethod.GET)
	public String addressForm(@PathVariable("id") Long id,Model model) {
		address address = new address();
		address.setUser(userService.getUser(id));
		model.addAttribute("address", address);
		return "address";
	}
	
	@RequestMapping(path = {"{id}/saveAddress"} , method = RequestMethod.POST)
	public String saveAddress(@Valid address address , @PathVariable("id") Long id , BindingResult bindingResult , Model model) {
		if (bindingResult.hasErrors()) {
			return "address";
		}
		User user = userService.getUser(id);
		address.setUser(user);
		addressService.save(address);
		return "redirect:/home";
	}
	
	

}
