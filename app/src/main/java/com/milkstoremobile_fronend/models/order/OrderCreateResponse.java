package com.milkstoremobile_fronend.models.order;

import java.util.List;

public class OrderCreateResponse {
    private String orderId;
    private String customerId;
    private double total;
    private String shippingAddress;
    private String orderStatus;
    private List<OrderDetail> orderDetails;

    // Constructor
    public OrderCreateResponse(String orderId, String customerId, double total, String shippingAddress, String orderStatus, List<OrderDetail> orderDetails) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
        this.shippingAddress = shippingAddress;
        this.orderStatus = orderStatus;
        this.orderDetails = orderDetails;
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getTotal() {
        return total;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    @Override
    public String toString() {
        return "OrderCreateResponse{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", total=" + total +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}