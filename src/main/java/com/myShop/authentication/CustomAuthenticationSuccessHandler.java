package com.myShop.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.myShop.entities.User;
import com.myShop.entities.userImage;


public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("CustomAuthenticationSuccessHandler.onAuthenticationSuccess()");
		User user = (User) authentication.getPrincipal();
		if(user.getUserImage() == null) {
			userImage ui = new userImage();
			ui.setImageTrack("anonymous-man.jpg");
			user.setUserImage(ui);
		}
		request.getSession().setAttribute("user", user);
		String referrer = request.getHeader("referer");
		response.sendRedirect(referrer);
	}

}
