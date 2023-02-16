package com.myShop.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.myShop.entities.User;
import com.myShop.entities.security.model.myUserDetails;
import com.myShop.repository.userRepo;

@Service
@SessionAttributes
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	userRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOpt = userRepo.findByEmail(email);
		if (userOpt.isPresent()) {
			if (!userOpt.get().getStatus().equals("active")) {
				throw new DisabledException("User is not active");
			} else {
				return userOpt.map(myUserDetails::new).get();
			}
		} else {
			throw new UsernameNotFoundException("Bad credentials");
		}
	}

}
