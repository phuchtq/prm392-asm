package com.milkstore.api.services;

import com.milkstore.models.message.MessageAIRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AiApiService {
    @POST("ai/recommend-milk")
    Call<ResponseBody> recommendMilk(@Body MessageAIRequest request);
}

