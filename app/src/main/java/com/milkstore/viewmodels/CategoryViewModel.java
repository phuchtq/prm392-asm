package com.milkstore.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.milkstore.api.services.CategoryApiService;
import com.milkstore.models.Category;
import com.milkstore.api.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class CategoryViewModel extends ViewModel {
    private final MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final CategoryApiService categoryApiService;

    public CategoryViewModel() {
        categoryApiService = ApiClient.getClient().create(CategoryApiService.class);
        fetchCategories(); // Lấy danh sách ngay khi khởi tạo
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchCategories() {
        Log.d("Category View Model", "Category View Model fetch data called");
        isLoading.setValue(true);
        categoryApiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    categories.setValue(response.body());
                } else {
                    errorMessage.setValue("Không thể tải danh sách danh mục");
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối API: " + t.getMessage());
            }
        });
    }

    public void addCategory(Category category) {
        isLoading.setValue(true);
        categoryApiService.createCategory(category).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                isLoading.setValue(false);
                Log.d("CategoryViewModel", "Create category status: " + response);
                if (response.isSuccessful()) {
                    fetchCategories(); // Refresh danh sách
                } else {
                    errorMessage.setValue("Lỗi khi thêm danh mục");
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối API: " + t.getMessage());
            }
        });
    }

    public void updateCategory(String id, Category category) {
        isLoading.setValue(true);
        categoryApiService.updateCategory(id, category).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    fetchCategories();
                } else {
                    errorMessage.setValue("Lỗi khi cập nhật danh mục");
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối API: " + t.getMessage());
            }
        });
    }

    public void deleteCategory(String id) {
        isLoading.setValue(true);
        categoryApiService.deleteCategory(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    fetchCategories();
                } else {
                    errorMessage.setValue("Lỗi khi xóa danh mục");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối API: " + t.getMessage());
            }
        });
    }
}

