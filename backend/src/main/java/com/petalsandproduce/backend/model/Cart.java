package com.petalsandproduce.backend.model;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int userId;
    private String sessionId;
    private ArrayList<CartItem> cartItems = new ArrayList<>();

    public Cart(int userId) {
        this.userId = userId;
    }

    public long getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ArrayList<CartItem> getCartItems() {
        return this.cartItems;
    }

    public void addToCart(CartItem item) {
        this.cartItems.add(item);
    }
}
