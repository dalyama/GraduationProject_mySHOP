package com.myShop.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myShop.VIEWcontroller.MyMailSenderController;
import com.myShop.authentication.CustomAuthenticationSuccessHandler;
import com.myShop.entities.MailRequest;
import com.myShop.entities.User;

@RequestMapping("/account")
@Controller
public class ActivationController {

	@Autowired
	userService userService;

	@GetMapping("/activation")
	public String getFormAccountActivation(Model model, @RequestParam(defaultValue = "null") String email) {

		if (email.equals("null")) {
			System.out.println("userController.getFormAccountActivation() null");
			model.addAttribute("msg", "You should enter your email.");
			// model.addAttribute("email", "e");
			return "activation";
		} else {
			User user = userService.findOne(email);
			if (user == null) {
				model.addAttribute("msg", "The email that you entred is not exist please enter email correctly");
				return "activation";
			} else {
				if (user.getStatus().equals("active")) {
					model.addAttribute("msg", "Your account is activated.");
					return "login";
				}
				model.addAttribute("email", email);
				return "activation";
			}
		}
	}

	@PostMapping("/activation")
	public String accountActivation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			@RequestParam("email") String email, @RequestParam("code") String code,Model model)
			throws IOException, ServletException {
		if (!email.equals("null")) {
			User user = userService.findOne(email);
			if (user.getStatus().equals(code)) {
				user.setStatus("active");
				userService.saveUser(user);
				model.addAttribute("msg", "Your account is activated. You can login now");
				return "login";

			}
		}

		return "activation";
	}

	@PostMapping("activation/checkEmail")
	public String checkEmail(@RequestParam("email") String email, Model model) {
		User user = userService.findOne(email);
		if (user == null) {
			model.addAttribute("msg", "The email that you entred is not exist please enter email correctly");
			return "activation";
		} else {
			return "redirect:/account/activation?email=" + email;
		}
	}

	@GetMapping("activation/checkEmail")
	public String checkEmail() {
		return "redirect:/account/activation";
	}

	@Autowired
	MyMailSenderController myMailSenderController;
	@GetMapping("/sendActivationCode")
	public String sendActivationCode(@RequestParam(defaultValue = "null") String email) {
		if(email.equals("null")) {
			return "redirect:/account/activation";
		}
		User user = userService.findOne(email);
		if( user != null) {
			//Send Verifying code
			MailRequest mailRequest = new MailRequest();
			mailRequest.setSubject("Actvation Code");
			mailRequest.setContent(user.getStatus());
			mailRequest.setTo(user.getEmail());
			mailRequest.setName(user.getName());
			myMailSenderController.sendEmail(mailRequest);
			return "redirect:/account/activation?email="+email;
		}
		else {
			return "";
		}
		
	}
	
	
	@Autowired
	MyUserDetailsService myUserDetailsService;

	CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler = null;

	public boolean setAuth(String email, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		System.out.println("ActivationController.setAuth()");
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);
		System.out.println(httpServletRequest.getContentLength());
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		try {
			customAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, httpServletResponse, auth);
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}

		return true;
	}

}
