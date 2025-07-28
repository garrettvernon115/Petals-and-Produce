package com.petalsandproduce.backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.petalsandproduce.backend.DTO.AdminOrderDTO;
import com.petalsandproduce.backend.DTO.CartItemDTO;
import com.petalsandproduce.backend.DTO.OrderItemDTO;
import com.petalsandproduce.backend.DTO.OrderRequestDTO;
import com.petalsandproduce.backend.DTO.OrderResponseDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Long submitOrder(OrderRequestDTO orderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User u) {
                user = u;
            }
        }

        Order order = new Order();
        order.setUser(user); 
        order.setStatus(OrderStatus.PENDING);

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

        Order savedOrder = orderRepository.save(order);

        if (user != null) {
            Cart cart = cartRepository.findByUserId(user.getId());
            if (cart != null) {
                cart.getCartItems().clear();
                cartRepository.save(cart);
            }
        }

        return savedOrder.getId();
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User u) {
                user = u;
            }
        }

        if (user == null) {
            throw new RuntimeException("User not authenticated");
        }

        List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(user);
        List<OrderResponseDTO> response = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItemDTO> itemDTOs = new ArrayList<>();
            for (OrderItem item : order.getItems()) {
                OrderItemDTO itemDTO = new OrderItemDTO(
                        item.getProduct().getName(),
                        item.getPrice(),
                        item.getQuantity()
                );
                itemDTOs.add(itemDTO);
            }

            OrderResponseDTO orderDTO = new OrderResponseDTO(
                    order.getId(),
                    order.getTotalAmount(),
                    order.getStatus().name(),
                    order.getOrderDate(),
                    itemDTOs
            );

            response.add(orderDTO);
        }

        return response;
    }

    
    @Transactional
    public Order updateOrderStatus(Long orderId, String newStatusStr) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(newStatusStr.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + newStatusStr);
        }

        if (newStatus != OrderStatus.PLACED &&
            newStatus != OrderStatus.READY_FOR_PICKUP &&
            newStatus != OrderStatus.PICKED_UP) {
            throw new IllegalArgumentException("Status must be one of: placed, ready for pickup, picked up");
        }


        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
  
        @Transactional(readOnly = true)
        public List<AdminOrderDTO> getAllOrdersForAdmin() {
            List<Order> orders = orderRepository.findAllWithItemsAndCustomer();

            List<AdminOrderDTO> response = new ArrayList<>();

            for (Order order : orders) {
                String customerName = order.getUser() != null
                    ? order.getUser().getName()
                    : "Guest";

                List<OrderItemDTO> itemDTOs = new ArrayList<>();
                for (OrderItem item : order.getItems()) {
                    itemDTOs.add(new OrderItemDTO(
                        item.getProduct().getName(),
                        item.getPrice(),
                        item.getQuantity()
                    ));
                }

                AdminOrderDTO dto = new AdminOrderDTO(
                    order.getId(),
                    customerName,
                    order.getOrderDate(),
                    order.getTotalAmount(),
                    order.getStatus().name(),
                    itemDTOs
                );

                response.add(dto);
            }

            return response;
    }
}