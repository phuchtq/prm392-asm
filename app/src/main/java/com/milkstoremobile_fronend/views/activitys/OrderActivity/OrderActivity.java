package com.milkstoremobile_fronend.views.activitys.OrderActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Login.LoginResponse;
import com.milkstoremobile_fronend.models.order.Order;
import com.milkstoremobile_fronend.sharereference.UserInfor.SharedPrefManager;
import com.milkstoremobile_fronend.viewmodels.OrderViewModel;
import com.milkstoremobile_fronend.views.activitys.MainActivity;
import com.milkstoremobile_fronend.views.activitys.ManagerDashboardActivity;
import com.milkstoremobile_fronend.views.adapters.Order.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private OrderViewModel orderViewModel;
    private ProgressBar progressBar;
    private TextView tvErrorMessage;
    private Button btnBackHome;

    private boolean isLoadingMore = false; // Kiểm soát việc tải thêm dữ liệu
    private List<Order> orderList = new ArrayList<>(); // Danh sách đơn hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_order);

        // Ánh xạ View
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        btnBackHome = findViewById(R.id.btnBackHome);

        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        LoginResponse user = SharedPrefManager.getInstance(this).getUser();
        if (user != null) {
            orderViewModel.setUserId(user.getCustomerId());
        }

        orderViewModel.fetchOrders();

        // Khởi tạo Adapter và xử lý sự kiện cập nhật trạng thái đơn hàng
        orderAdapter = new OrderAdapter(this, order -> orderViewModel.updateOrderStatus(order.getOrderId(), "Delivered"));

        orderRecyclerView.setAdapter(orderAdapter);

        // Lắng nghe dữ liệu từ ViewModel
        orderViewModel.getOrdersLiveData().observe(this, orders -> {
            if (orders != null && !orders.isEmpty()) {
                orderList.clear();
                orderList.addAll(orders);
                orderAdapter.setOrders(orderList);
                tvErrorMessage.setVisibility(View.GONE);
            } else {
                tvErrorMessage.setVisibility(View.VISIBLE);
                tvErrorMessage.setText("Không có đơn hàng nào!");
            }
            isLoadingMore = false;
        });

        orderViewModel.getIsLoading().observe(this, isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        orderViewModel.getErrorMessage().observe(this, errorMsg -> {
            if (errorMsg != null) {
                tvErrorMessage.setText(errorMsg);
                tvErrorMessage.setVisibility(View.VISIBLE);
                Toast.makeText(OrderActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
            isLoadingMore = false;
        });

        boolean isAdmin = user != null && user.getRoleName().equals("ADMIN");
        // Xử lý nút quay về trang quản lý
        btnBackHome.setOnClickListener(v -> onBackHomeListener(isAdmin));

        // Gọi API lấy danh sách đơn hàng lần đầu
        orderViewModel.fetchOrders();

        // Lắng nghe sự kiện kéo xuống cuối danh sách
        setupScrollListener();
    }

    private void onBackHomeListener(boolean isAdmin) {
        Intent intent = isAdmin ? new Intent(OrderActivity.this, ManagerDashboardActivity.class) : new Intent(OrderActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupScrollListener() {
        orderRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && dy > 0) { // Kiểm tra cuộn xuống
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoadingMore && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        isLoadingMore = true;
                        orderViewModel.fetchOrders(); // Tải thêm dữ liệu
                    }
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        refreshOrders(); // Gọi lại API khi quay lại màn hình
    }

    private void refreshOrders() {
        orderList.clear(); // Xóa danh sách cũ để tránh hiển thị dữ liệu lỗi thời
        orderAdapter.setOrders(orderList); // Cập nhật lại UI
        orderViewModel.fetchOrders(); // Gọi API để lấy danh sách đơn hàng mới nhất
    }
}
