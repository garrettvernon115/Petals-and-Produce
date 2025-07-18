package com.petalsandproduce.backend.service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petalsandproduce.backend.DTO.CartItemDTO;
import com.petalsandproduce.backend.DTO.OrderRequestDTO;
import com.petalsandproduce.backend.DTO.OrderResponseDTO;
import com.petalsandproduce.backend.DTO.OrderItemDTO;
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
    public void submitOrder(OrderRequestDTO orderRequest, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new RuntimeException("User not found");
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
            orderItem.setPrice(product.getPrice()); // Capture current price

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItems.add(orderItem);
        }

        order.setTotalAmount(total);
        order.setItems(orderItems);

        orderRepository.save(order); 

       
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart != null) {
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersForUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("User not found");

        List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(user);
        List<OrderResponseDTO> result = new ArrayList<>();

        for (Order order : orders) {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setId(order.getId());
            dto.setOrderDate(order.getOrderDate());
            dto.setStatus(order.getStatus().name());

            List<OrderItemDTO> items = new ArrayList<>();
            for (OrderItem oi : order.getItems()) {
                OrderItemDTO itemDto = new OrderItemDTO();
                itemDto.setName(oi.getProduct().getName());
                itemDto.setQuantity(oi.getQuantity());
                itemDto.setUnitPrice(oi.getPrice());
                items.add(itemDto);
            }
            dto.setItems(items);
            result.add(dto);
        }
        return result;
    }
}