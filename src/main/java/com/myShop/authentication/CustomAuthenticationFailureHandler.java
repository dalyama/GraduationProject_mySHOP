package com.myShop.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String errorMsg = null;
		if (exception.getMessage().equals("User is not active")) {
			errorMsg = "notactive";
		} 
		else if (exception.getMessage().equals("Bad credentials")) {
			errorMsg = "notfound";
		}		
		response.sendRedirect("/login?error="+errorMsg); 
	}
	


}
