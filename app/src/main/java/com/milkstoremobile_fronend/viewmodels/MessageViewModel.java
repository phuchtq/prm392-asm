package com.milkstoremobile_fronend.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.milkstoremobile_fronend.api.ApiClient;
import com.milkstoremobile_fronend.api.services.AiApiService;
import com.milkstoremobile_fronend.models.message.MessageAIRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends ViewModel {

    private final MutableLiveData<String> aiAnswer = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final AiApiService aiApiService = ApiClient.getAiApiService();

    public LiveData<String> getAiAnswer() {
        return aiAnswer;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void askAi(String question) {
        if (question == null || question.trim().isEmpty()) {
            aiAnswer.postValue("Vui lòng nhập câu hỏi.");
            return;
        }

        isLoading.postValue(true);
        Log.d("AI", "Gửi câu hỏi: " + question);

        MessageAIRequest request = new MessageAIRequest(question);

        aiApiService.recommendMilk(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                isLoading.postValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String result = response.body().string();
                        aiAnswer.postValue(result);
                    } catch (Exception e) {
                        aiAnswer.postValue("Lỗi đọc dữ liệu phản hồi từ AI.");
                        Log.e("AI", "Lỗi đọc response", e);
                    }
                } else {
                    aiAnswer.postValue("AI không có phản hồi.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isLoading.postValue(false);
                aiAnswer.postValue("Lỗi khi kết nối AI: " + t.getMessage());
                Log.e("AI", "Lỗi kết nối", t);
            }
        });
    }
}
