package com.petalsandproduce.backend.model;

import java.util.ArrayList;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private ArrayList<CartItem> cartItems;

    public Cart() {

    }

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<CartItem> getCartItems() {
        return this.cartItems;
    }

    public void addToCart(CartItem item) {
        this.cartItems.add(item);
    }


}
