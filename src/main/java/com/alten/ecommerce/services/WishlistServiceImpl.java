package com.alten.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alten.ecommerce.dto.WishListDto;
import com.alten.ecommerce.entity.Product;
import com.alten.ecommerce.entity.User;
import com.alten.ecommerce.entity.Wishlist;
import com.alten.ecommerce.repository.ProductRepository;
import com.alten.ecommerce.repository.UserDao;
import com.alten.ecommerce.repository.WishlistRepository;
import com.alten.ecommerce.security.SecurityUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserDao userDao;
	@Autowired
	private WishlistRepository wishlistRepository;

	@Autowired
	private SecurityUtil securityUtil;

	// add Product To Wish List
	public ResponseEntity<?> addProductToWishList(WishListDto wishListDto) {
		Optional<Product> optionalProduct = productRepository.findById(wishListDto.getProductId());
		User user = securityUtil.getCurrentUser();
		if (optionalProduct.isPresent()) {
			// Check if the product is already in the wishlist for the user
			boolean isProductInWishlist = wishlistRepository.existsByProductAndUser(optionalProduct.get(), user);
			if (isProductInWishlist) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
			}
			// Product not in wishlist, proceed to add
			Wishlist wishlist = new Wishlist();
			wishlist.setProduct(optionalProduct.get());
			wishlist.setUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(wishlistRepository.save(wishlist).getWishListDto());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}

	// remove Product From Wish List
	@Transactional
	public ResponseEntity<?> removeProductFromWishlist(Integer productId) {
		wishlistRepository.deleteByProductId(productId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// get tWish ListBy User Id :
	public List<WishListDto> getWishListByUserId() {
		User user = securityUtil.getCurrentUser();
		return wishlistRepository.findAllByUserId(user.getId()).stream().map(Wishlist::getWishListDto)
				.collect(Collectors.toList());
	}

}
