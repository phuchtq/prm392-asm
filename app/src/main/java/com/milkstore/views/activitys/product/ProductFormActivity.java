package com.milkstore.views.activitys.product;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.milkstore.R;
import com.milkstore.models.Category;
import com.milkstore.models.Product;
import com.milkstore.viewmodels.CategoryViewModel;
import com.milkstore.viewmodels.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductFormActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText edtProductName, edtProductPrice, edtProductQuantity, edtProductDescription;
    private Spinner spinnerCategory;
    private ImageView imgProduct;
    private Button btnChooseImage, btnSaveProduct;
    private ProductViewModel productViewModel;
    private CategoryViewModel cateViewModel;
    private List<String> categories;
    private String selectedCate = ""; // Initialize with empty string
    private ArrayAdapter<String> categoryAdapter;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        // Initialize all views ONCE
        edtProductName = findViewById(R.id.edt_product_name);
        edtProductPrice = findViewById(R.id.edt_product_price);
        edtProductQuantity = findViewById(R.id.edt_product_quantity);
        edtProductDescription = findViewById(R.id.edt_product_description);
        spinnerCategory = findViewById(R.id.spinner_category);
        imgProduct = findViewById(R.id.img_product);
        btnChooseImage = findViewById(R.id.btn_choose_image);
        btnSaveProduct = findViewById(R.id.btn_save_product);

        // Initialize categories list
        categories = new ArrayList<>();
        categories.add("Select category..."); // Add default item

        // Create adapter
        categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // Initialize ViewModels
        cateViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        boolean isNull = cateViewModel != null;
        Log.d("Product", "Category ViewModel is Null: " + isNull);

        // Load categories
        setCategories();

        // Setup spinner listener with null checks
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Add null check
                Object selectedItem = parent.getItemAtPosition(position);
                if (selectedItem != null) {
                    String selected = selectedItem.toString();
                    // Don't set the placeholder as selected category
                    if (!selected.equals("Select category...")) {
                        selectedCate = selected;
                        Log.d("Product", "Selected category: " + selectedCate);
                    } else {
                        selectedCate = "";
                    }
                } else {
                    selectedCate = "";
                    Log.w("Product", "Selected item is null at position: " + position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCate = "";
            }
        });

        btnSaveProduct = findViewById(R.id.btn_save_product);
        btnSaveProduct.setOnClickListener(v -> saveProduct());

        btnChooseImage = findViewById(R.id.btn_choose_image);
        btnChooseImage.setOnClickListener(v -> openImageChooser());

    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgProduct.setImageURI(imageUri);
        }
    }

    private void saveProduct() {
        String name = edtProductName.getText().toString().trim();
        String priceStr = edtProductPrice.getText().toString().trim();
        String quantityStr = edtProductQuantity.getText().toString().trim();
        String description = edtProductDescription.getText().toString().trim();

        // Validate inputs
        if (name.isEmpty()) {
            edtProductName.setError("Vui lòng nhập tên sản phẩm");
            return;
        }
        if (priceStr.isEmpty()) {
            edtProductPrice.setError("Vui lòng nhập giá");
            return;
        }
        if (quantityStr.isEmpty()) {
            edtProductQuantity.setError("Vui lòng nhập số lượng");
            return;
        }
        if (description.isEmpty()) {
            edtProductDescription.setError("Vui lòng nhập mô tả");
            return;
        }
        if (selectedCate.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        // Find category ID
        String categoryId = "CAT_001"; // Default
        List<Category> categoryList = cateViewModel.getCategories().getValue();
        if (categoryList != null) {
            for (Category data : categoryList) {
                if (data.getSubCategory().equalsIgnoreCase(selectedCate)) {
                    categoryId = data.getId();
                    break;
                }
            }
        }

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);
        String image = (imageUri != null) ? imageUri.toString() : "";

        Product product = new Product(image, categoryId, description, price, quantity, name);
        productViewModel.addProduct(product);

        Toast.makeText(this, "Sản phẩm đã được lưu", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setCategories() {
        cateViewModel.getCategories().observe(this, categoryList -> {
            if (categoryList != null && !categoryList.isEmpty()) {
                categories.clear();
                categories.add("Select category..."); // Add placeholder first

                for (Category c : categoryList) {
                    if (c != null) {
                        categories.add(c.getSubCategory());
                    }
                }

                categoryAdapter.notifyDataSetChanged();
                Log.d("Product", "Categories loaded: " + categories.size());
            }
        });
    }
}