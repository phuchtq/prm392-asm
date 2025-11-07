package com.milkstoremobile_fronend.views.activitys.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Category;
import com.milkstoremobile_fronend.viewmodels.CategoryViewModel;
import com.milkstoremobile_fronend.views.adapters.Category.CategoryAdapter;

import java.util.ArrayList;

public class CategoryListActivity extends ComponentActivity {

    private CategoryViewModel categoryViewModel;
    private CategoryAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_category);
        FloatingActionButton btnAdd = findViewById(R.id.btn_add_cateogry);
        progressBar = findViewById(R.id.progress_bar_category);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Adapter mới dùng List<Category> và click callback Category
        adapter = new CategoryAdapter(new ArrayList<>(), new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                Toast.makeText(CategoryListActivity.this, "Clicked: " + category.getBrandName(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        // ✅ Khởi tạo ViewModel
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        // ✅ Quan sát dữ liệu danh mục
        categoryViewModel.getCategories().observe(this, categories -> {
            adapter.updateData(categories);
        });

        // ✅ Quan sát trạng thái loading
        categoryViewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        categoryViewModel.fetchCategories(); // Gọi API khi mở activity

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryListActivity.this, CategoryFormActivity.class);
            startActivity(intent);
        });
    }
}
