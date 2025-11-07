package com.milkstore.api.services;

import com.milkstore.models.Login.LoginRequest;
import com.milkstore.models.Login.LoginResponse;
import com.milkstore.models.Register.RegisterRequest;
import com.milkstore.models.Register.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest request);
}
