package com.itfi.crud_api.controllers;

import com.itfi.crud_api.core.exceptions.ResourceNotFoundException;
import com.itfi.crud_api.data.dto.OrderRequest;
import com.itfi.crud_api.data.entities.*;
import com.itfi.crud_api.data.repositories.CartRepository;
import com.itfi.crud_api.data.repositories.OrderRepository;
import com.itfi.crud_api.data.repositories.ProductRepository;
import com.itfi.crud_api.data.repositories.UserRepository;
import com.itfi.crud_api.data.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{email}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable String email) {
        User user = getCurrentUser(email);
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        User user = getCurrentUser(request);
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found or empty"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus("NEW");
        order.setShippingAddress(request.getShippingAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // Calculate total and create order items
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(
                    cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        cartRepository.save(cart);

        return ResponseEntity.ok(savedOrder);
    }

    private User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private User getCurrentUser(OrderRequest request) {
        /*String username = SecurityContextHolder.getContext().getAuthentication().getName();*/

        return userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
