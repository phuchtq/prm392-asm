package com.milkstoremobile_fronend.views.activitys.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.api.ApiClient;
import com.milkstoremobile_fronend.api.services.AiApiService;
import com.milkstoremobile_fronend.models.message.MessageAIRequest;
import com.milkstoremobile_fronend.models.message.MessageAiResponse;
import com.milkstoremobile_fronend.views.adapters.Messages.MessageAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private AiApiService aiApiService;
    private final List<String> messageList = new ArrayList<>();
    private MessageAdapter adapter;

    private RecyclerView recyclerView;
    private EditText edtMessage;
    private Button btnSend;
    private LottieAnimationView loadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chat);

        // Init API service
        aiApiService = ApiClient.getAiApiService();

        // Init UI
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        loadingAnimation = findViewById(R.id.loadingAnimation);
        loadingAnimation.setVisibility(View.GONE);

        adapter = new MessageAdapter(messageList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Gửi tin nhắn
        btnSend.setOnClickListener(v -> {
            String content = edtMessage.getText().toString().trim();
            if (content.isEmpty()) return;

            // Thêm tin nhắn người dùng
            messageList.add("Bạn: " + content);
            adapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.scrollToPosition(messageList.size() - 1);
            edtMessage.setText("");

            // Hiển thị animation loading
            loadingAnimation.setVisibility(View.VISIBLE);
            loadingAnimation.playAnimation();

            // Gọi AI API
            aiApiService.recommendMilk(new MessageAIRequest(content)).enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            String result = response.body().string();
                            Log.d("AI_RESPONSE", "AI trả lời: " + result);

                            // Thêm kết quả AI vào danh sách
                            messageList.add("AI: " + result);
                            runOnUiThread(() -> {
                                adapter.notifyItemInserted(messageList.size() - 1);
                                recyclerView.scrollToPosition(messageList.size() - 1);
                                loadingAnimation.cancelAnimation();
                                loadingAnimation.setVisibility(View.GONE);
                            });

                        } catch (Exception e) {
                            Log.e("AI_ERROR", "Lỗi khi đọc response", e);
                        }
                    } else {
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Không có nội dung lỗi";
                            Log.e("AI_ERROR", "Phản hồi không hợp lệ - Code: " + response.code() + ", Body: " + errorBody);
                        } catch (Exception e) {
                            Log.e("AI_ERROR", "Lỗi khi đọc errorBody", e);
                        }
                    }
                }



                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("AI_ERROR", "Lỗi kết nối API: " + t.getMessage(), t);
                }
            });


        });
    }

    @Override
    protected void onDestroy() {
        if (loadingAnimation != null) {
            loadingAnimation.cancelAnimation();
        }
        super.onDestroy();
    }
}
