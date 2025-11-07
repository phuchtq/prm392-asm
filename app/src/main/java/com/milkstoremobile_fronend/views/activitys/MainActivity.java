package com.milkstoremobile_fronend.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.milkstoremobile_fronend.Fragment.HomeFragment;
import com.milkstoremobile_fronend.Fragment.CategoryFragment;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Login.LoginResponse;
import com.milkstoremobile_fronend.sharereference.Cart.CartManager;
import com.milkstoremobile_fronend.sharereference.UserInfor.SharedPrefManager;
import com.milkstoremobile_fronend.views.activitys.cart.CartActivity;
import com.milkstoremobile_fronend.views.activitys.chat.ChatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Hiển thị HomeFragment mặc định khi mở app
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();

        // Kiểm tra trạng thái đăng nhập và cập nhật menu
        updateBottomNavigationMenu(bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_milk) {
                // Khi bấm vào tab "Sữa", cũng hiển thị HomeFragment
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_category) {
                selectedFragment = new CategoryFragment();
            } else if (item.getItemId() == R.id.nav_cart) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (item.getItemId() == R.id.nav_chat) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_login_logout) {
                LoginResponse user = SharedPrefManager.getInstance(this).getUser();
                boolean isLoggedIn = user != null && user.getCustomerId() != null;

                if (isLoggedIn) {
                    logoutUser();
                    updateBottomNavigationMenu(bottomNavigationView);
                } else {
                    Intent intent = new Intent(MainActivity.this, AuthenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
            }

            return true;
        });
    }

    private void updateBottomNavigationMenu(BottomNavigationView bottomNavigationView) {
        LoginResponse user = SharedPrefManager.getInstance(this).getUser();
        boolean isLoggedIn = user != null && user.getCustomerId() != null;

        if (isLoggedIn) {
            bottomNavigationView.getMenu().findItem(R.id.nav_login_logout)
                    .setIcon(R.drawable.logout_icon)
                    .setTitle(R.string.logout);
        } else {
            bottomNavigationView.getMenu().findItem(R.id.nav_login_logout)
                    .setIcon(R.drawable.login_icon)
                    .setTitle(R.string.login);
        }
    }

    private void logoutUser() {
        // Xóa thông tin người dùng
        SharedPrefManager.getInstance(this).clearUser();

        // Xóa giỏ hàng
        CartManager.getInstance(this).clearCart();

        // Hiển thị thông báo
        Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();

        // Chuyển về màn hình đăng nhập
        Intent intent = new Intent(MainActivity.this, AuthenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
