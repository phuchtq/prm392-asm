package com.milkstoremobile_fronend.api.services;


import com.milkstoremobile_fronend.models.Product;
import com.milkstoremobile_fronend.response.ProductResponse;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductService {

    // Lấy danh sách sản phẩm
    @GET("products")
    Call<List<Product>> getProducts();




    // Lấy sản phẩm theo ID
    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") String id);

    // Thêm sản phẩm mới
    @POST("products")
    Call<Product> createProduct(@Body Product product);

    // Cập nhật sản phẩm
    @PUT("products/{id}")
    Call<Product> updateProduct(@Path("id") String id, @Body Product product);

    // Xóa sản phẩm
    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") String id);
}

