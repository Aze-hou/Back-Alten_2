package com.alten.ecommerce.service.user;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alten.ecommerce.constents.AltenConstants;
import com.alten.ecommerce.entity.User;
import com.alten.ecommerce.repository.UserDao;
import com.alten.ecommerce.security.CustomerUsersDetailsService;
import com.alten.ecommerce.security.JwtFilter;
import com.alten.ecommerce.security.JwtUtil;
import com.alten.ecommerce.utils.AltenUtils;
import com.alten.ecommerce.utils.EmailUtils;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomerUsersDetailsService customerUsersDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	JwtFilter jwtFilter;
	@Autowired
	EmailUtils emailUtils;
	@Autowired
	private PasswordEncoder passwordEncoder;

	// sign Up :
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		log.info("Inside Sign up {}", requestMap);
		try {
			if (validateSignUpMap(requestMap)) {
				User user = userDao.findByEmailId(requestMap.get("email"));
				if (Objects.isNull(user)) {
					userDao.save(getUserFromMap(requestMap));
					return AltenUtils.getResponseEntity("Successfully Registered.", HttpStatus.OK);
				} else {
					return AltenUtils.getResponseEntity(AltenConstants.EMAIL_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
				}
			} else {
				return AltenUtils.getResponseEntity(AltenConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return AltenUtils.getResponseEntity(AltenConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
	}

	// validate SignUp Map :
	private boolean validateSignUpMap(Map<String, String> requestMap) {
		if (requestMap.containsKey("username") && requestMap.containsKey("firstname") && requestMap.containsKey("email")
				&& requestMap.containsKey("password")) {
			return true;
		} else {
			return false;
		}
	}

	// get User From Map :
	private User getUserFromMap(Map<String, String> requestMap) {
		User user = new User();
		user.setUsername(requestMap.get("username"));
		user.setFirstname(requestMap.get("firstname"));
		user.setEmail(requestMap.get("email"));
		user.setPassword(passwordEncoder.encode(requestMap.get("password")));
		user.setRole("user");

		return user;
	}

	// create Admin Account :
	@PostConstruct
	public void createAdminAccount() {
		String adminEmail = "admin@admin.com";

		// Check if an account with the admin email already exists
		if (userDao.findByEmail(adminEmail) == null) {
			User user = new User();
			user.setEmail(adminEmail);
			user.setFirstname("admin");
			user.setUsername("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userDao.save(user);

			System.out.println("Admin account created successfully!");
		} else {
			System.out.println("Admin account already exists.");
		}
	}

	// login:
	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
		log.info("Inside Login");
		try {
			// Authenticate the user with email and password
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));

			if (auth.isAuthenticated()) {
				return new ResponseEntity<>(
						"{\"token\":\"" + jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
								customerUsersDetailsService.getUserDetail().getRole()) + "\"}",
						HttpStatus.OK);
			}
		} catch (Exception ex) {
			log.error("Authentication error: {}", ex.getMessage());
		}
		return new ResponseEntity<>("{\"message\":\"Bad Credentials.\"}", HttpStatus.BAD_REQUEST);
	}

	// check Token
	@Override
	public ResponseEntity<String> checkToken() {
		return AltenUtils.getResponseEntity("true", HttpStatus.OK);
	}

}
