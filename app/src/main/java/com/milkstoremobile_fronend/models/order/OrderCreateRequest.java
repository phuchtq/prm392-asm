package com.milkstoremobile_fronend.models.order;
import java.util.List;

public class OrderCreateRequest {
    private String customerId;
    private String shippingAddress;
    private List<OrderDetail> orderDetails;

    public OrderCreateRequest(String customerId, String shippingAddress, List<OrderDetail> orderDetails) {
        this.customerId = customerId;
        this.shippingAddress = shippingAddress;
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderCreateRequest{" +
                "customerId='" + customerId + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
