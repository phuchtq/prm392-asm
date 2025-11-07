package com.milkstoremobile_fronend.views.activitys.milk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.milkstoremobile_fronend.models.cart.ProductDetailOfCart;
import com.milkstoremobile_fronend.views.activitys.cart.CartActivity;
import com.milkstoremobile_fronend.models.Product;
import com.milkstoremobile_fronend.R;



import android.widget.Toast;
import com.milkstoremobile_fronend.sharereference.Cart.CartManager;

public class MilkDetailActivity extends AppCompatActivity {
    private ImageView imgMilk;
    private TextView tvName, tvType, tvPrice, tvDetail;
    private Button btnBuy;
    private CartManager cartManager;

    private Product milk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_detail);

        imgMilk = findViewById(R.id.imgMilk);
        tvName = findViewById(R.id.tvName);
        tvType = findViewById(R.id.recycler_view_category);
        tvPrice = findViewById(R.id.tvPrice);
        tvDetail = findViewById(R.id.tvDetail);
        btnBuy = findViewById(R.id.btnBuy);

        // Khởi tạo CartManager
        cartManager = CartManager.getInstance(this);

        // Nhận dữ liệu từ Intent
         milk = (Product) getIntent().getSerializableExtra("milk");
        // Debug log để kiểm tra dữ liệu milk nhận được
        Log.d("MilkDetailActivity", "Milk received: " + milk);
        if (milk != null) {
            Glide.with(this)
                    .load(milk.getImage())
                    .placeholder(R.drawable.milk1)
                    .error(R.drawable.error_image)
                    .into(imgMilk);

            tvName.setText(milk.getProductName());
            tvType.setText("Type: " + milk.getCategoryId());
            tvPrice.setText("Price: $" + milk.getPrice());
            tvDetail.setText(milk.getDescription());

            btnBuy.setOnClickListener(v -> {
                addToCart(milk);
            });
        }
    }

    private void addToCart(Product milk) {
        if (milk.getId() == null) {
            Toast.makeText(this, "Lỗi: ID sản phẩm bị null!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductDetailOfCart productDetail = new ProductDetailOfCart(
                milk.getId(),
                milk.getProductName(),
                1,
                milk.getPrice(),
                milk.getImage(),
                milk.getDescription(),
                "Available"
        );

        CartManager.getInstance(this).addProductToCart(productDetail);
        Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
        // Quay về Homepage sau khi thêm thành công
        Intent intent = new Intent(this, CartActivity.class); // Đổi thành Activity chính của bạn
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Kết thúc Activity hiện tại

    }
}
