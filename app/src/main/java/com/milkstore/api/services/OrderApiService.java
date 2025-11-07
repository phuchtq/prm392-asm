package com.milkstore.api.services;

import com.milkstore.models.order.OrderCreateRequest;
import com.milkstore.models.order.OrderCreateResponse;
import com.milkstore.models.order.OrderStatusUpdate;
import com.milkstore.models.order.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApiService {
    @POST("orders")
    Call<OrderCreateResponse> createOrder(@Body OrderCreateRequest orderCreateRequest);

    @GET("orders")
    Call<List<Order>> getOrders();  // Không truyền page

    @GET("orders/user/{userId}")
    Call<List<Order>> getUserOrders(@Path("userId") String userId);

    @PUT("orders/{orderId}")
    Call<Void> updateOrderStatus(@Path("orderId") String orderId, @Body OrderStatusUpdate statusUpdate);

}