package com.milkstoremobile_fronend.api.services;

import com.milkstoremobile_fronend.models.message.MessageAIRequest;
import com.milkstoremobile_fronend.models.message.MessageAiResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AiApiService {


    @POST("ai/recommend-milk")
    Call<ResponseBody> recommendMilk(@Body MessageAIRequest request);





}

