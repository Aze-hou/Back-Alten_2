package com.alten.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.ecommerce.dto.ContactDto;
import com.alten.ecommerce.utils.EmailUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

	@Autowired
	private EmailUtils emailUtils;

	// send message
	@PostMapping
	@CrossOrigin("*")
	public void sendMessage(@RequestBody ContactDto contactDto) throws Exception {
		StringBuilder body = new StringBuilder("Message from : ").append(contactDto.getEmail());
		body.append(" \n\n Message : ").append(contactDto.getMessage());
		emailUtils.sendSimpleMessage("azeddine.hou@gmail.com", "From Form Contact", body.toString(), null);
	}

}
