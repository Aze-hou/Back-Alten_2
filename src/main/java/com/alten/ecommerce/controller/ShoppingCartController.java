package com.alten.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.ecommerce.dto.CardItemDto;
import com.alten.ecommerce.dto.UserCardsDto;
import com.alten.ecommerce.services.ShoppingCartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	// Endpoint to add product to cart
	@PostMapping("/addToCard")
	public void addProductToCart(@RequestBody CardItemDto dto) {
		this.shoppingCartService.addProductToCart(dto);
	}

	// Endpoint to remove product from cart
	@DeleteMapping("/remove/{productId}")
	public ResponseEntity<String> removeProductFromCart(@PathVariable("productId") Integer productId) throws Exception {
		shoppingCartService.removeProductFromCart(productId);
		return ResponseEntity.ok("Produit supprimé du panier");
	}

	// Endpoint to get the shopping cart
	@GetMapping("/current")
	@CrossOrigin("*")
	public UserCardsDto getShoppingCart() {
		return shoppingCartService.getShoppingCartById();
	}

}
