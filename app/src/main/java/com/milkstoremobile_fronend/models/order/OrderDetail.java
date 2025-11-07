package com.milkstoremobile_fronend.models.order;

public class OrderDetail {
    private String productId;
    private int quantity;

    public OrderDetail(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters và Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
