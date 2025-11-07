package com.milkstoremobile_fronend.api.services;

import com.milkstoremobile_fronend.models.Login.LoginRequest;
import com.milkstoremobile_fronend.models.Login.LoginResponse;
import com.milkstoremobile_fronend.models.Register.RegisterRequest;
import com.milkstoremobile_fronend.models.Register.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest request);
}
