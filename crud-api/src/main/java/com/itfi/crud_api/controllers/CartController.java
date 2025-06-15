package com.itfi.crud_api.controllers;

import com.itfi.crud_api.core.exceptions.ResourceNotFoundException;
import com.itfi.crud_api.data.dto.CartItemRequest;
import com.itfi.crud_api.data.entities.Cart;
import com.itfi.crud_api.data.entities.CartItem;
import com.itfi.crud_api.data.entities.Product;
import com.itfi.crud_api.data.entities.User;
import com.itfi.crud_api.data.repositories.CartRepository;
import com.itfi.crud_api.data.repositories.ProductRepository;
import com.itfi.crud_api.data.repositories.UserRepository;
import com.itfi.crud_api.data.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping("/{email}")
    public ResponseEntity<Cart> getCurrentCart(@PathVariable String email) {
        User user = getCurrentUser(email);
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewCart(user));

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody CartItemRequest request) {
        User user = getCurrentUser(request);
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewCart(user));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setAddedAt(LocalDateTime.now());

        cartItemRepository.save(cartItem);

        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cartItemRepository.delete(cartItem);

        return ResponseEntity.ok().build();
    }

    private Cart createNewCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    private User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private User getCurrentUser(CartItemRequest request) {
        /*String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));*/

        return userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
