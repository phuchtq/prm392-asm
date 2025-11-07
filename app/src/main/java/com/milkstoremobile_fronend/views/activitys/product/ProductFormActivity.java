package com.milkstoremobile_fronend.views.activitys.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Category;
import com.milkstoremobile_fronend.models.Product;
import com.milkstoremobile_fronend.viewmodels.CategoryViewModel;
import com.milkstoremobile_fronend.viewmodels.ProductViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductFormActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; // Mã request chọn ảnh
    private EditText edtProductName, edtProductPrice, edtProductQuantity, edtProductDescription;
    private Spinner spinnerCategory;
    private ImageView imgProduct;
    private Button btnChooseImage, btnSaveProduct;
    private ProductViewModel productViewModel;

    private CategoryViewModel cateViewModel;

    private List<String> categories;

    private String selectedCate;
    private ArrayAdapter<String> categoryAdapter;

    private Uri imageUri; // Biến lưu đường dẫn ảnh đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);
        spinnerCategory = findViewById(R.id.spinner_category);
        categories = new ArrayList<>();

// Create adapter FIRST
        categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

// THEN create ViewModel and observe
                cateViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
                boolean isNull = cateViewModel != null;
                Log.d("Product", "Category VIew Model is Null: " + isNull);
        setCategories();


        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object selectedItem = parent.getItemAtPosition(position);
                if (selectedItem != null) {
                    selectedCate = selectedItem.toString();
                } else {
                    onNothingSelected(parent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCate = "";
            }
        });


        // Ánh xạ các view
        edtProductName = findViewById(R.id.edt_product_name);
        edtProductPrice = findViewById(R.id.edt_product_price);
        edtProductQuantity = findViewById(R.id.edt_product_quantity);
        edtProductDescription = findViewById(R.id.edt_product_description);
        spinnerCategory = findViewById(R.id.spinner_category);
        imgProduct = findViewById(R.id.img_product);
        btnChooseImage = findViewById(R.id.btn_choose_image);
        btnSaveProduct = findViewById(R.id.btn_save_product);

        //spinnerCategory.setAdapter();


        // Khởi tạo ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Xử lý sự kiện chọn ảnh
        btnChooseImage.setOnClickListener(v -> openImageChooser());

        // Xử lý sự kiện lưu sản phẩm
        btnSaveProduct.setOnClickListener(v -> saveProduct());
    }

    // Mở trình chọn ảnh từ thư viện
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*"); // Chỉ chọn ảnh
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Nhận kết quả sau khi chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData(); // Lưu đường dẫn ảnh đã chọn
            imgProduct.setImageURI(imageUri); // Hiển thị ảnh lên ImageView
        }
    }

    // Lưu sản phẩm mới vào ViewModel
    private void saveProduct() {
        String name = edtProductName.getText().toString().trim();
        String priceStr = edtProductPrice.getText().toString().trim();
        String quantityStr = edtProductQuantity.getText().toString().trim();
        String description = edtProductDescription.getText().toString().trim();
        String categoryId = "CAT_001";
        for (var data: cateViewModel.getCategories().getValue()) {
            if (data.getSubCategory().equalsIgnoreCase(selectedCate)) {
                categoryId = data.getId();
                selectedCate = "";
                break;
            }
        }


        if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty() || description.isEmpty()) {
            edtProductName.setError("Vui lòng nhập tên sản phẩm");
            edtProductPrice.setError("Vui lòng nhập giá");
            edtProductQuantity.setError("Vui lòng nhập số lượng");
            edtProductDescription.setError("Vui lòng nhập mô tả");
            selectedCate = "";
            return;
        }

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);
        String image = (imageUri != null) ? imageUri.toString() : ""; // Lưu đường dẫn ảnh nếu có

        Product product = new Product(image, categoryId, description, price, quantity, name);
        productViewModel.addProduct(product);

        finish(); // Đóng activity sau khi lưu
    }

    private void setCategories() {
        cateViewModel.getCategories().observe(this, categoryList -> {
            if (categoryList != null && categoryList.size() > 0) {
                categories.clear();
                categories.add("Select category...");
                for (Category c : categoryList) {

                        categories.add(c.getSubCategory());

                }
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }
}
