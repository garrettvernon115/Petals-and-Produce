package com.petalsandproduce.backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.petalsandproduce.backend.DTO.CartItemDTO;
import com.petalsandproduce.backend.DTO.OrderRequestDTO;
import com.petalsandproduce.backend.model.Cart;
import com.petalsandproduce.backend.model.Order;
import com.petalsandproduce.backend.model.OrderItem;
import com.petalsandproduce.backend.model.OrderStatus;
import com.petalsandproduce.backend.model.Product;
import com.petalsandproduce.backend.model.User;
import com.petalsandproduce.backend.repository.CartRepository;
import com.petalsandproduce.backend.repository.OrderItemRepository;
import com.petalsandproduce.backend.repository.OrderRepository;
import com.petalsandproduce.backend.repository.ProductRepository;
import com.petalsandproduce.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public void submitOrder(OrderRequestDTO orderRequest) {
        User user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            user = (User) authentication.getPrincipal();
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setPickedUp(false);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItemDTO item : orderRequest.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItems.add(orderItem);
        }

        order.setTotalAmount(total);
        order.setItems(orderItems);

        orderRepository.save(order);

        if (user != null) {
            Cart cart = cartRepository.findByUserId(user.getId());
            if (cart != null) {
                cart.getCartItems().clear();
                cartRepository.save(cart);
            }
        }
    }
}
