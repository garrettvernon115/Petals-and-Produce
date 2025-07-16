package com.petalsandproduce.backend.request;
 
public class UpdateCartRequest {
    private long productId;
    private int newQuantity;
 
    public long getProductId() {
        return productId;
    }
 
    public void setProductId(long productId) {
        this.productId = productId;
    }
 
    public int getNewQuantity() {
        return newQuantity;
    }
 
    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }
}