package com.myShop.VIEWcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myShop.entities.User;

@Controller
@RequestMapping("")
public class loginController {
	
	@GetMapping("/login")
    public String loginPage(@RequestParam(defaultValue = "null") String logout,
                            @RequestParam(defaultValue = "null") String error,
                            Model model, HttpSession session) {
		/* Start Check if User already logged in */
		User user = new User();
		if (session.getAttribute("user") != null) {
			user = (User) session.getAttribute("user");
			System.out.println("user logged in with email : "+user.getEmail());
			return "redirect:/home";
		}
		/* End Check if User already logged in */
		
        String errorMessage = null;
        if(error != null) {
        	if (error.equals("notactive")) {
                errorMessage = "Your account is not activate yet. Please activate your account ";
        	}
        	else if (error.equals("notfound")) {
                errorMessage = "Username or Password is incorrect !!";	
        	}
        }
        if(logout != null && logout.equals("logout")) {
            errorMessage = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }
  
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        //return "redirect:/login?logout=true";
        return "redirect:/home";
    }
    
    @RequestMapping("/login/process")
    public String loginProcessingUrl(){
    	System.out.println("loginController.performLogin()");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth.isAuthenticated()) {
    		System.out.println(false);
    	}
    	else {
    		System.out.println(true);
    	}
    	System.out.println("public String performLogin()");
    	return "redirect:/home";
    } 
    
    @RequestMapping("/s")
    public String failureLogin(){
    	System.out.println("loginController.performLogin()");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth.isAuthenticated()) {
    		System.out.println(false);
    	}
    	else {
    		System.out.println(true);
    	}
    	System.out.println("public String performLogin()");
    	return "redirect:/home";
    }
    /*
    @Autowired
    userService userService;
	@PostMapping("login")
	public void login (@RequestParam("username") String username , @RequestParam("password") String password,@Valid user user, BindingResult bindingResult,HttpSession session) {
		System.out.println("You have enter into void login method.");
		user isExist = userService.findOne(username);
		
		user = isExist;
		if (isExist != null) {
			if(isExist.getPassword().equals(user.getPassword())) {
				System.out.println("The Email and Password are true...");
				//session.setAttribute("user", user);
			}
			else {
				System.out.println("The Email and Password are false...");
			}
		}
		else {
			System.out.println("Email is not exist...");
		}
		//return "index";
	}
	*/
	
    
}