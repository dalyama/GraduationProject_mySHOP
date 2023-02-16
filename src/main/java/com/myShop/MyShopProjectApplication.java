package com.myShop;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.myShop.entities.Roles;
import com.myShop.controller.admin.adsController;
import com.myShop.entities.User;
import com.myShop.repository.RolesRepo;
import com.myShop.repository.userRepo;
import com.myShop.services.userService;

@SpringBootApplication
//@ComponentScan({ "com.myShop", "com.myShop.controller" })
public class MyShopProjectApplication extends SpringBootServletInitializer implements CommandLineRunner {
	// GitHub added successfully 15-4-2020
	@Autowired
	userService userService;

	@Autowired
	userRepo userRepo;

	@Autowired
	RolesRepo rolesRepo;

	public static void main(String[] args) {
		new File(adsController.uploadDirectory).mkdir();
		SpringApplication.run(MyShopProjectApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MyShopProjectApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		if (userRepo.findByEmail("support@support.com").isEmpty()) {

			// first we have insert some roles
			List<Roles> list = new ArrayList<Roles>();
			list.add(new Roles(1, "USER"));
			list.add(new Roles(2, "ADMIN"));
			list.add(new Roles(3, "MANAGER"));
			rolesRepo.saveAll(list);

			// add Manager
			User admin = new User();
			admin.setEmail("support@support.com");
			admin.setPhone("09304968894");
			admin.setRegisterationDate(new Date());
			admin.setName("Admin");
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			admin.setPassword(encoder.encode("92213250"));
			admin.setStatus("active");
			admin.setId((long) 4);
			User newAdmin = userService.saveUser(admin);
			if (newAdmin != null) {
				Roles roleM = new Roles(3, "MANAGER");
				Roles roleA = new Roles(2, "ADMIN");
				Set<Roles> roles = new HashSet<Roles>();
				roles.add(roleM);
				roles.add(roleA);
				newAdmin.setRoles(roles);
				userService.saveUser(newAdmin);
			}

			// add User
			User user = new User();
			user.setEmail("user@user.com");
			user.setPhone("07518429146");
			user.setRegisterationDate(new Date());
			user.setName("User");
			user.setPassword(encoder.encode("92213250"));
			user.setStatus("active");
			user.setId((long) 4);
			User newUser = userService.saveUser(user);
			if (newUser != null) {
				Roles role = new Roles();
				role.setId(1);
				role.setRole("USER");
				Set<Roles> roles = new HashSet<Roles>();
				roles.add(role);
				newUser.setRoles(roles);
				userService.saveUser(newUser);

			} 
			else {
				System.out.println("MyShopApplication.run() : working successfully");
			}
		}
		*/
	}
	
}
