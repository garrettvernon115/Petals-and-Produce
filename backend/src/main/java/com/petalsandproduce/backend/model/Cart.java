package com.petalsandproduce.backend.model;

import java.util.ArrayList;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int userId;
    private int sessionId;
    private ArrayList<CartItem> cartItems;

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

    public int getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public ArrayList<CartItem> getCartItems() {
        return this.cartItems;
    }

    public void addToCart(CartItem item) {
        this.cartItems.add(item);
    }
}
