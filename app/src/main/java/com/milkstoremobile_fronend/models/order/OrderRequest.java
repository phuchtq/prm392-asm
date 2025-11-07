package com.milkstoremobile_fronend.models.order;

import java.util.List;

public class OrderRequest {
    private String customerId;
    private String shippingAddress;
    private List<OrderDetail> orderDetails;

    public OrderRequest(String customerId, String shippingAddress, List<OrderDetail> orderDetails) {
        this.customerId = customerId;
        this.shippingAddress = shippingAddress;
        this.orderDetails = orderDetails;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public static class OrderDetail {
        private String productId;
        private int quantity;

        public OrderDetail(String productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public String getProductId() {
            return productId;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
