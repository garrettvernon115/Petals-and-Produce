package com.petalsandproduce.backend.repository;

import java.util.List;

import com.petalsandproduce.backend.model.Order;
import com.petalsandproduce.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
    
}
