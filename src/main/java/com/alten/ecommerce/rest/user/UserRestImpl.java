package com.alten.ecommerce.rest.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.alten.ecommerce.constents.AltenConstants;
import com.alten.ecommerce.service.user.UserService;
import com.alten.ecommerce.utils.AltenUtils;

@RestController
public class UserRestImpl implements UserRest {

	@Autowired
	private UserService userService;

	// Sign Up :
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		try {
			return userService.signUp(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return AltenUtils.getResponseEntity(AltenConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// login :
	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
		try {
			return userService.login(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return AltenUtils.getResponseEntity(AltenConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// check Token
	@Override
	public ResponseEntity<String> checkToken() {
		try {
			return userService.checkToken();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return AltenUtils.getResponseEntity(AltenConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
