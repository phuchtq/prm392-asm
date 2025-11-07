package com.milkstoremobile_fronend.viewmodels;

import android.util.Log;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.milkstoremobile_fronend.api.ApiClient;

import com.milkstoremobile_fronend.api.services.ProductService;
import com.milkstoremobile_fronend.models.Product;
import com.milkstoremobile_fronend.response.ProductResponse;


import java.util.List;
import java.util.function.DoubleToIntFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {
    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private ProductService productService;
    private final String shared_image = "https://digitalcontent.api.tesco.com/v2/media/ghs/e238daba-f6e3-4dfc-9caf-36c223514324/f810ea3e-bde2-423c-b6d3-c3fd03e1125a_500732259.jpeg?h=960&w=960";

    public ProductViewModel() {
        productService =  ApiClient.getClient().create(ProductService.class);
        fetchProducts();
    }

    // Lấy danh sách sản phẩm
    public LiveData<List<Product>> getProducts() {
        return products;
    }

    // Trạng thái loading
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    // Gọi API để lấy danh sách sản phẩm
    public void fetchProducts() {
        isLoading.setValue(true);

        productService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> productList = response.body();
                    Log.d("ProductViewModel", "Số lượng sản phẩm nhận được: " + productList.size());
                    for (Product p : productList) {
                        Log.d("ProductViewModel", "Product: " + p.getProductName());
                    }
                    products.setValue(productList);
                } else {
                    Log.e("ProductViewModel", "Lỗi API: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                isLoading.setValue(false);
                Log.e("ProductViewModel", "Gọi API thất bại", t);
            }
        });
    }



    // Thêm sản phẩm
    public void addProduct(Product product) {
        product.setImage(shared_image);
        product.setStatusDescription("Status Description");
        isLoading.setValue(true);
        productService.createProduct(product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                isLoading.setValue(false);
                fetchProducts();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                isLoading.setValue(false);
            }
        });
    }

    // Cập nhật sản phẩm
    public void updateProduct(String id, Product product) {
        isLoading.setValue(true);
        productService.updateProduct(id, product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                isLoading.setValue(false);
                fetchProducts();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                isLoading.setValue(false);
            }
        });
    }

    // Xóa sản phẩm
    public void deleteProduct(String id) {
        isLoading.setValue(true);
        productService.deleteProduct(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                fetchProducts();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
            }
        });
    }
}

