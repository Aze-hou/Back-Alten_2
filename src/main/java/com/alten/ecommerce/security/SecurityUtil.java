package com.alten.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.alten.ecommerce.entity.User;
import com.alten.ecommerce.repository.UserDao;

@Component
public class SecurityUtil {

	@Autowired
	UserDao userDao;

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return this.userDao.findByEmail(userDetails.getUsername());
		}
		return null;
	}
}