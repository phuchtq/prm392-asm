package com.milkstoremobile_fronend.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.milkstoremobile_fronend.api.ApiClient;
import com.milkstoremobile_fronend.api.services.OrderApiService;
import com.milkstoremobile_fronend.models.order.Order;
import com.milkstoremobile_fronend.models.order.OrderResponse;
import com.milkstoremobile_fronend.models.order.OrderStatusUpdate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends ViewModel {
    private final MutableLiveData<List<Order>> ordersLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final OrderApiService apiService;

    private int currentPage = 1;

    private String userId;
    private boolean hasMorePages = true;  // Kiểm tra còn dữ liệu để tải không

    public OrderViewModel() {
        apiService = ApiClient.getClient().create(OrderApiService.class);
        this.userId = "";
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LiveData<List<Order>> getOrdersLiveData() {
        return ordersLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchOrders() {
        if (isLoading.getValue() != null && isLoading.getValue()) return; // Tránh gọi API nhiều lần

        if (!hasMorePages) return; // Dừng khi không còn trang để tải

        isLoading.setValue(true);
        if (!this.userId.isEmpty()) {
            apiService.getUserOrders(this.userId).enqueue(new Callback<List<Order>>() {
                @Override
                public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                    Log.e("onResponse: ", response.toString());
                    isLoading.setValue(false);

                    if (response.isSuccessful() && response.body() != null) {
                        ordersLiveData.setValue(response.body());
                        hasMorePages = false; // Không phân trang
                    } else {
                        errorMessage.setValue("Không thể tải đơn hàng");
                    }
                }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                    isLoading.setValue(false);
                    errorMessage.setValue("Lỗi kết nối: " + t.getMessage());
                }
            });

        }
    }

    public void updateOrderStatus(String orderId, String newStatus) {
        apiService.updateOrderStatus(orderId, new OrderStatusUpdate(newStatus)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    currentPage = 1;  // Reset trang về 1 sau khi cập nhật
                    ordersLiveData.setValue(new ArrayList<>()); // Xóa danh sách cũ
                    hasMorePages = true;
                    fetchOrders(); // Tải lại danh sách
                } else {
                    errorMessage.setValue("Cập nhật thất bại");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorMessage.setValue("Lỗi kết nối khi cập nhật trạng thái");
            }
        });
    }
}
