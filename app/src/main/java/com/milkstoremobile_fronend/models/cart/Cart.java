package com.milkstoremobile_fronend.models.cart;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {
    private String customerId;
    private String shippingAddress;
    private List<ProductDetailOfCart> orderDetails;

    // Constructor
    public Cart(String customerId, String shippingAddress, List<ProductDetailOfCart> orderDetails) {
        this.customerId = customerId;
        this.shippingAddress = shippingAddress;
        this.orderDetails = orderDetails;
    }

    // Getters và Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<ProductDetailOfCart> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<ProductDetailOfCart> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
