package com.milkstoremobile_fronend.api.services;

import com.milkstoremobile_fronend.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryApiService {
    @GET("categories")
    Call<List<Category>> getCategories();

    @POST("categories")
    Call<Category> createCategory(@Body Category category);

    @PUT("categories/{id}")
    Call<Category> updateCategory(@Path("id") String id, @Body Category category);

    @DELETE("categories/{id}")
    Call<Void> deleteCategory(@Path("id") String id);
}

