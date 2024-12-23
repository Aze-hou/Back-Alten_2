package com.alten.ecommerce.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alten.ecommerce.dto.WishListDto;

public interface WishlistService {

	ResponseEntity<?> addProductToWishList(WishListDto wishListDto);

	List<WishListDto> getWishListByUserId();

	ResponseEntity<?> removeProductFromWishlist(Integer productId);

}
