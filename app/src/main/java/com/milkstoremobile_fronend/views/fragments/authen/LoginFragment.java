package com.milkstoremobile_fronend.views.fragments.authen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.api.ApiClient;
import com.milkstoremobile_fronend.api.services.AuthService;
import com.milkstoremobile_fronend.models.Login.LoginRequest;
import com.milkstoremobile_fronend.models.Login.LoginResponse;
import com.milkstoremobile_fronend.sharereference.UserInfor.SharedPrefManager;
import com.milkstoremobile_fronend.views.activitys.AuthenActivity;
import com.milkstoremobile_fronend.views.activitys.MainActivity;
import com.milkstoremobile_fronend.views.activitys.ManagerDashboardActivity;
import com.milkstoremobile_fronend.views.activitys.cart.CartActivity;

import retrofit2.Call;


public class LoginFragment extends Fragment {
    private EditText username,password;
    private TextView registerLink;
    private Button loginBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        bindView(view);

        loginBtn.setOnClickListener(v -> {
            String User = username.getText().toString().trim();
            String Pass = password.getText().toString().trim();

            if (!isValidUsername(User)) {
                username.setError("Tên đăng nhập không hợp lệ");
                username.requestFocus();
                return;
            }

            if (Pass.isEmpty()) {
                password.setError("Mật khẩu không được trống");
                password.requestFocus();
                return;
            }

            if (password.length() < 1) {
                password.setError("Mật khẩu phải có ít nhất 6 ký tự");
                password.requestFocus();
                return;
            }
            // Gọi API đăng nhập
            AuthService authService = ApiClient.getClient().create(AuthService.class);
            Call<LoginResponse> call = authService.loginUser(new LoginRequest(User, Pass));

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse loginResponse = response.body();
                        if (!loginResponse.getCustomerId().isEmpty()) {
                            Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            resetFields();

                            // Lưu thông tin user vào SharedPreferences
                            SharedPrefManager.getInstance(getContext()).saveUser(loginResponse);

                            // Kiểm tra roleName để điều hướng
                            if ("ADMIN".equalsIgnoreCase(loginResponse.getRoleName())) {
                                Intent intent = new Intent(getContext(), ManagerDashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getContext().startActivity(intent);
                            } else if ("USER".equalsIgnoreCase(loginResponse.getRoleName())) {
                                Intent intent = new Intent(getContext(), MainActivity.class); // Thay bằng màn hình người dùng
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getContext().startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Vai trò không hợp lệ!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Sai username hoặc password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Lỗi đăng nhập, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        registerLink.setOnClickListener(v -> {
            if (getActivity() instanceof AuthenActivity) {
                ((AuthenActivity) getActivity()).switchFragment(new RegisterFragment());
            }
        });

        return view;
    }

    public void bindView(View view){
        username = view.findViewById(R.id.et_username);
        password = view.findViewById(R.id.et_password);
        loginBtn = view.findViewById(R.id.btn_login);
        registerLink = view.findViewById(R.id.register_link);
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 3 && username.matches("^[a-zA-Z0-9._-]+$");
    }
    public void resetFields() {
        username.setText("");
        password.setText("");
    }
}
