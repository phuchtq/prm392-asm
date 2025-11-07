package com.milkstoremobile_fronend.models.order;

public class OrderStatusUpdate {
    private String status;

    public OrderStatusUpdate(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}