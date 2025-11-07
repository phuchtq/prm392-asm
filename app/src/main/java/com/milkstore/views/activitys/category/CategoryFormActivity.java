package com.milkstore.views.activitys.category;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.milkstore.models.Category;
import com.milkstore.viewmodels.CategoryViewModel;
import com.milkstore.R;
public class CategoryFormActivity extends ComponentActivity {
    private TextInputEditText editTextBrand, editTextAge, editTextSub, editTextPackage, editTextSource;
    private Button buttonSave;
    private CategoryViewModel categoryViewModel;
    private Category editingCategory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_form);

        editTextBrand = findViewById(R.id.edit_brand);
        editTextAge = findViewById(R.id.edit_age);
        editTextSub = findViewById(R.id.edit_sub);
        editTextPackage = findViewById(R.id.edit_package);
        editTextSource = findViewById(R.id.edit_source);
        buttonSave = findViewById(R.id.button_save_category);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        if (getIntent().hasExtra("category")) {
            editingCategory = (Category) getIntent().getSerializableExtra("category");
            editTextBrand.setText(editingCategory.getBrandName());
            editTextAge.setText(editingCategory.getAgeRange());
            editTextSub.setText(editingCategory.getSubCategory());
            editTextPackage.setText(editingCategory.getPackageType());
            editTextSource.setText(editingCategory.getSource());
        }

        buttonSave.setOnClickListener(v -> saveCategory());
    }

    private void saveCategory() {
        String brand = editTextBrand.getText().toString();
        String age = editTextAge.getText().toString();
        String sub = editTextSub.getText().toString();
        String pack = editTextPackage.getText().toString();
        String source = editTextSource.getText().toString();

        Category category = new Category(brand, age, sub, pack, source);
        Log.d("Category Form Activity", "Category info: " + category.getAgeRange());

        if (editingCategory == null) {
            categoryViewModel.addCategory(category);
            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        } else {
            categoryViewModel.updateCategory(editingCategory.getId(), category);
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}

