package com.milkstoremobile_fronend.views.activitys.cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.api.ApiClient;
import com.milkstoremobile_fronend.api.services.OrderApiService;
import com.milkstoremobile_fronend.models.Login.LoginResponse;
import com.milkstoremobile_fronend.models.cart.Cart;
import com.milkstoremobile_fronend.models.cart.ProductDetailOfCart;
import com.milkstoremobile_fronend.models.order.OrderCreateRequest;
import com.milkstoremobile_fronend.models.order.OrderCreateResponse;
import com.milkstoremobile_fronend.models.order.OrderDetail;
import com.milkstoremobile_fronend.sharereference.Cart.CartManager;
import com.milkstoremobile_fronend.sharereference.UserInfor.SharedPrefManager;
import com.milkstoremobile_fronend.views.activitys.AuthenActivity;
import com.milkstoremobile_fronend.views.activitys.MainActivity;
import com.milkstoremobile_fronend.views.activitys.payment.PaymentActivity;
import com.milkstoremobile_fronend.views.adapters.Product.ProductCartAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerViewProducts;
    private ProductCartAdapter productCartAdapter;
    private List<ProductDetailOfCart> productList;
    private TextView tvTotalPrice;
    private Button btnBackToHomepage, btnPayment;
    private CartManager cartManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kiểm tra người dùng có đăng nhập hay không
        if (!isUserLoggedIn()) {
            redirectToLogin();
            return;
        }

        setContentView(R.layout.activity_card_list);

        // Khởi tạo CartManager
        cartManager = CartManager.getInstance(this);

        // Ánh xạ View
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnBackToHomepage = findViewById(R.id.btnBackToHomepage);
        btnPayment = findViewById(R.id.btnPayment);

        // Cấu hình RecyclerView
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        // Lấy danh sách sản phẩm từ giỏ hàng
        loadCartData();

        // Xử lý sự kiện nút "Back to Homepage"
        btnBackToHomepage.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Xử lý sự kiện "Thanh toán"
        btnPayment.setOnClickListener(view -> {
            // Lấy thông tin user từ SharedPreferences
            LoginResponse user = SharedPrefManager.getInstance(this).getUser();
            if (user == null) {
                Toast.makeText(this, "Vui lòng đăng nhập trước khi thanh toán!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra giỏ hàng có sản phẩm không
            if (productList == null || productList.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống, không thể đặt hàng!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy danh sách sản phẩm từ giỏ hàng
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (ProductDetailOfCart product : productList) {
                orderDetails.add(new OrderDetail(product.getProductId(), product.getQuantity()));
            }

            // Tạo request đơn hàng
            OrderCreateRequest orderRequest = new OrderCreateRequest(
                    user.getCustomerId(), // Lấy ID user từ SharedPrefManager
                    "123 Main Street111", // Hoặc cho phép người dùng nhập địa chỉ
                    orderDetails
            );

            // Gọi API tạo order
            OrderApiService orderApiService = ApiClient.getClient().create(OrderApiService.class);
            orderApiService.createOrder(orderRequest).enqueue(new Callback<OrderCreateResponse>() {
                @Override
                public void onResponse(Call<OrderCreateResponse> call, Response<OrderCreateResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        OrderCreateResponse orderResponse = response.body();
                        Toast.makeText(CartActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();

                        Log.e("CartOrderActivity", "onResponse: " + orderResponse.getOrderId() + ", status: " + orderResponse.getOrderStatus());

                        // Xóa giỏ hàng sau khi đặt hàng thành công
                        cartManager.clearCart();
                        productList.clear();
                        productCartAdapter.notifyDataSetChanged();
                        updateTotalPrice();

                        // Chuyển hướng đến màn hình thanh toán
                        Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("CartOrderActivity", "Lỗi khi đặt hàng: " + response.code());
                        Toast.makeText(CartActivity.this, "Đặt hàng không thành công", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OrderCreateResponse> call, Throwable t) {
                    Log.e("CartOrderActivity", "Lỗi mạng: ", t);
                    Toast.makeText(CartActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

    }

    // Kiểm tra người dùng có đăng nhập không
    private boolean isUserLoggedIn() {
        LoginResponse user = SharedPrefManager.getInstance(this).getUser();
        return user != null && user.getRoleName() != null;
    }

    // Chuyển hướng về trang đăng nhập nếu chưa đăng nhập
    private void redirectToLogin() {
        Intent intent = new Intent(this, AuthenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Tải dữ liệu giỏ hàng từ CartManager
    private void loadCartData() {
        Cart cart = cartManager.getCart();
        productList = cart.getOrderDetails();

        productCartAdapter = new ProductCartAdapter(productList, this);
        recyclerViewProducts.setAdapter(productCartAdapter);

        updateTotalPrice();
    }


    // Cập nhật tổng giá trị giỏ hàng
    private void updateTotalPrice() {
        double total = 0;
        for (ProductDetailOfCart product : productList) {
            total += product.getPrice() * product.getQuantity();
        }
        tvTotalPrice.setText("Tổng giá: " + total + "đ");
    }
}
