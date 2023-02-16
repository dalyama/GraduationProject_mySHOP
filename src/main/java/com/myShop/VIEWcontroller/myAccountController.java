package com.myShop.VIEWcontroller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myShop.entities.address;
import com.myShop.entities.User;
import com.myShop.entities.userImage;
import com.myShop.repository.userImageRepo;
import com.myShop.services.addressService;
import com.myShop.services.productDetailsService;
import com.myShop.services.productService;
import com.myShop.services.userService;

@RequestMapping("/myAccount")
@Controller
public class myAccountController {
	
	@Autowired
	userService userService;
	
	@Autowired
	productDetailsService productDetailsService;
	
	@Autowired
	productService productService;
	
	@Autowired
	userImageRepo userImageRepo;
	
	@Autowired
	addressService addressService;
	
	
	public static String uploadDirectory = "src/main/resources/static/images/uploads/usersDB";
	@GetMapping("")
	public String myAccount(Model model,
			@RequestParam(defaultValue = "null") String passwordMsg,
			@RequestParam(defaultValue = "null") String personalInfoMsg,
			@RequestParam(defaultValue = "null") String addressMsg,
			Principal principal) {
		System.out.println("myAccountController.myAccount()");
		System.out.println("principal is : "+principal.getName());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findOne(auth.getName());
		System.out.println("sdgsg : "+user.getEmail());
		if (user.getUserImage() == null) {
			System.out.println("user image is null");
		}
		else {
			System.out.println("user image is not null");
		}
		model.addAttribute("user", user);
		userImage userImage = userImageRepo.findOneByUser_Email(auth.getName());
		if (userImage != null) {
			model.addAttribute("userImage", userImage);
		}
		else {
			userImage ui= new userImage();
			ui.setImageTrack("anonymous-man.jpg");
			ui.setUser(user);
			model.addAttribute("userImage", ui);
		}
		
		
		/* Address Handler */
		address address = addressService.findOneByUser_Email(user.getEmail());
		if(address != null) {
			model.addAttribute("address", address);
		}
		else {
			model.addAttribute("address", new address());
		}
		
		/* Set Massages */ 
		if (!passwordMsg.equals("null")) {
			if (passwordMsg.equals("ok")) {
				passwordMsg = "The password updated successfully.";
			}
			else {
				passwordMsg = "Failed.\nPassword and Confirm Passwrod must be matched. please repeat password again.";		
			}
			model.addAttribute("passwordMsg", passwordMsg);
		}
		if(!personalInfoMsg.equals("null")) {
			if(personalInfoMsg.equals("ok")) {
				personalInfoMsg = "The personal information updated successfully.";
			}
			else {
				personalInfoMsg = "Failed.";
			}
			
			model.addAttribute("personalInfoMsg", personalInfoMsg);
		}
		if(!addressMsg.equals("null")) {
			if (addressMsg.equals("ok")) {
				addressMsg = "The address updated successfully.";
			}
			else {
				addressMsg = "Updating Address Failed. Try other time.";
			}

			model.addAttribute("addressMsg", addressMsg);
		}
		return "myAccount";
	}
	
	@PostMapping("/update")
	public String updateImage(@RequestParam("img") MultipartFile[] img, HttpSession session) throws InterruptedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findOne(auth.getName());
		System.out.println("myAccountController.updateImage().authGetName : ");
		System.out.println("user update image : "+user.getEmail());
		userImage userImage = userImageRepo.findOneByUser_Email(user.getEmail());
		if(userImage != null) {
			File f = new File(uploadDirectory +"\\"+ userImage.getImageTrack());
			System.out.println("myAccountController.updateImage() go go go");
			if(f.exists()) {
				System.out.println("myAccountController.updateImage() bah bah bah");
				f.delete();
				//userImage = uploadImage(userImage, img);
			}
			System.out.println("myAccountController.updateImage() i will try");
			userImage = uploadImage(userImage, img);
			
		}
		else {
			System.out.println("myAccountController.updateImage() sheh sheh sheh");
			userImage ui = new userImage();
			ui.setUser(user);
			userImage = uploadImage(ui, img);
		}
		if (userImage != null) {
			System.out.println("myAccountController.updateImage()"+userImage.getUser().getEmail());
			User userSession = (User) session.getAttribute("user");
			userSession.setUserImage(userImage);
			session.setAttribute("user", userSession);
			Thread.sleep(3000); 
		}
		
		return "redirect:/myAccount";
	}
	
	
	public userImage uploadImage(userImage userImage , MultipartFile[] img) {
		System.out.println("myAccountController.uploadImage()");
		StringBuilder fileNames = new StringBuilder();
		for(MultipartFile file : img) {
			String fileName = getRandomString();
			fileName =  fileName+"-"+file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
			fileNames.append(file.getOriginalFilename());
			try {
				byte [] image=file.getBytes();
				userImage.setImage(image);
				userImage.setImageTrack(fileName);
				Files.write(fileNameAndPath, file.getBytes());
			}catch(Exception e) {
				System.out.println("myAccountController.uploadImage()");
				return null;
			}
		}
		return userImageRepo.save(userImage);
	}
	
	
	@PostMapping("/newPassword")
	public String updatePassword(@RequestParam("password") String password , @RequestParam("confirmPassword") String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			return "redirect:/myAccount?passwordMsg=error";
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findOne(auth.getName().toString());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));
		userService.saveUser(user);
		return "redirect:/myAccount?passwordMsg=ok";
	}
	
	@PostMapping("/updatePersonalInfo")
	public String updatePersonalInfo(
			@RequestParam("username") String username,
			@RequestParam("birthdate") String birthdate,
			@RequestParam("phone") String phone ) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findOne(auth.getName().toString());
		try {
			
			user.setBirthdate(birthdate);
			user.setPhone(phone);
			user.setName(username);
			userService.saveUser(user);
		}catch(Exception e) {
			return "redirect:/myAccount?personalInfoMsg=error";
		}
		
		return "redirect:/myAccount?personalInfoMsg=ok";
	}
	
	
	@PostMapping("/updateAddress")
	public String updateAddress(@Valid address address) {
		System.out.println("myAccountController.updateAddress()");
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findOne(auth.getName().toString());
			address ad = addressService.findOneByUser_Email(user.getEmail());
			if (ad != null) {
				System.out.println(address.getCity() + " *** "+address.getCountry()+" *** "+
						address.getStreet()+" *** "+address.getDescription()+" *** "+address.getPostNo());
				ad.setCity(address.getCity());
				ad.setCountry(address.getCountry());
				ad.setDescription(address.getDescription());
				ad.setPostNo(address.getPostNo());
				ad.setStreet(address.getStreet());
				ad.setUser(user);
				addressService.save(address);
			}
			else {
				address.setUser(user);
				addressService.save(address);
			}
			System.out.println("2");
	
			System.out.println("3");
		}
		catch (Exception e) {
			System.out.println("Error : "+e);
			return "redirect:/myAccount?addressMsg=error";
		}
		return "redirect:/myAccount?addressMsg=ok";
	}
	
	
	
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
	
	
}
