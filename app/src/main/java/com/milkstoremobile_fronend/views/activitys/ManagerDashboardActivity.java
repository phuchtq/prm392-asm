package com.milkstoremobile_fronend.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.ComponentActivity;
import androidx.cardview.widget.CardView;

import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Login.LoginResponse;
import com.milkstoremobile_fronend.sharereference.UserInfor.SharedPrefManager;
import com.milkstoremobile_fronend.views.activitys.OrderActivity.OrderActivity;
import com.milkstoremobile_fronend.views.activitys.category.CategoryListActivity;
import com.milkstoremobile_fronend.views.activitys.product.ProductListActivity;
//import com.milkstoremobile_fronend.views.activitys.product.ProductListActivity;
//import com.milkstoremobile_fronend.views.activitys.order.OrderListActivity;

public class ManagerDashboardActivity extends ComponentActivity {
    private CardView cvMngCate;
    private CardView cvMngPrd;
    private CardView cvMngOrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);

        cvMngOrd = findViewById(R.id.cardViewOrder);
        cvMngCate = findViewById(R.id.cardViewCate);
        cvMngPrd = findViewById(R.id.cardViewProduct);

        cvMngOrd.setOnClickListener(v -> onOrderManagementClick(v));

        LoginResponse user = SharedPrefManager.getInstance(this).getUser();
        if (user == null || !user.getRoleName().equals("ADMIN")) {
            cvMngPrd.setVisibility(View.INVISIBLE);
            cvMngCate.setVisibility(View.INVISIBLE);
            return;
        }

        cvMngPrd.setOnClickListener(v -> onProductManagementClick(v));
        cvMngCate.setOnClickListener(v -> onCategoryManagementClick(v));
    }

    public void onCategoryManagementClick(View view) {
        Intent intent = new Intent(this, CategoryListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onProductManagementClick(View view) {
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onOrderManagementClick(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onLogoutClick(View view) {
        // Xóa thông tin user khỏi SharedPreferences
        SharedPrefManager.getInstance(this).clearUser();

        // Chuyển về màn hình đăng nhập và xóa tất cả các activity trước đó
        Intent intent = new Intent(this, AuthenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Đóng activity hiện tại
    }
}
//btnBackHomeFromDashBoard
