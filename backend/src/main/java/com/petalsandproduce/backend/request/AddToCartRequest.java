package com.petalsandproduce.backend.request;

import com.petalsandproduce.backend.model.Cart;

public class AddToCartRequest {
    private long id;
    private long productId;
    private int quantity;

    public AddToCartRequest() {}

    public AddToCartRequest(long id, long productId, int quantity) {
        super();
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
