package com.alten.ecommerce.controller;

import java.util.List;

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

import com.alten.ecommerce.dto.WishListDto;
import com.alten.ecommerce.services.WishlistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/cart")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	// add Product To Wish List
	@PostMapping("/wishlist")
	@CrossOrigin("*")
	public ResponseEntity<?> addProductToWishList(@RequestBody WishListDto wishListDto) {
		return wishlistService.addProductToWishList(wishListDto);
	}

	// remove Product From Wish List
	@DeleteMapping("/wishlist/{productId}")
	@CrossOrigin("*")
	public ResponseEntity<?> removeProductFromWishlist(@PathVariable("productId") Integer productId) {
		return wishlistService.removeProductFromWishlist(productId);
	}

	// get Wish List By User Id :
	@GetMapping("/wishlist")
	@CrossOrigin("*")
	public ResponseEntity<List<WishListDto>> getWishListByUserId() {
		return ResponseEntity.ok(wishlistService.getWishListByUserId());
	}

}
