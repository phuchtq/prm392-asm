package com.milkstoremobile_fronend.views.fragments.authen;

import android.os.Bundle;
import android.util.Log;
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
import com.milkstoremobile_fronend.models.Register.RegisterRequest;
import com.milkstoremobile_fronend.models.Register.RegisterResponse;
import com.milkstoremobile_fronend.views.activitys.AuthenActivity;

import java.io.IOException;

public class RegisterFragment extends Fragment {

    private EditText etFirstName, etLastName, etEmail, etPhoneNumber, etUserName, etPassword;
    private TextView loginLink;
    private Button btnRegister;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resgitster, container, false);
        bindView(view);

        btnRegister.setOnClickListener(v -> {
            if (validateInput()) {
                RegisterRequest request = new RegisterRequest(
                        etFirstName.getText().toString().trim(),
                        etLastName.getText().toString().trim(),
                        etEmail.getText().toString().trim(),
                        etPhoneNumber.getText().toString().trim(),
                        etUserName.getText().toString().trim(),
                        etPassword.getText().toString().trim()
                );

                AuthService apiService = ApiClient.getClient().create(AuthService.class);
                Call<RegisterResponse> call = apiService.registerUser(request);

                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        int statusCode = response.code();  // Lấy mã trạng thái HTTP
                        Log.d("API_STATUS", "Status Code: " + statusCode); // Debug Logcat

                        if (statusCode == 201) {  // Đăng ký thành công
                            Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            resetFields();
                            // Chuyển về màn hình đăng nhập
                            if (getActivity() instanceof AuthenActivity) {
                                ((AuthenActivity) getActivity()).switchFragment(new LoginFragment());
                            }
                        } else if(statusCode == 400) {  // Nếu mã khác 201 -> Email đã tồn tại
                            Toast.makeText(getContext(), "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }



                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Log.e("API_ERROR", "Lỗi kết nối khi gọi API đăng ký", t);
                        Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        resetFields();
                        // Chuyển về màn hình đăng nhập
                        if (getActivity() instanceof AuthenActivity) {
                            ((AuthenActivity) getActivity()).switchFragment(new LoginFragment());
                        }
                    }
                });
            }
        });


        loginLink.setOnClickListener(v -> {
            if (getActivity() instanceof AuthenActivity) {
                ((AuthenActivity) getActivity()).switchFragment(new LoginFragment());
            }
        });

        return view;
    }

    public void bindView(View view){
         etFirstName = view.findViewById(R.id.et_register_firstName);
         etLastName = view.findViewById(R.id.et_register_lastName);
         etEmail = view.findViewById(R.id.et_register_email);
         etPhoneNumber = view.findViewById(R.id.et_register_phoneNumber);
         etUserName = view.findViewById(R.id.et_register_userName);
         etPassword = view.findViewById(R.id.et_register_password);
         btnRegister = view.findViewById(R.id.btn_register);
         loginLink = view.findViewById(R.id.login_link);
    }

    // Hàm kiểm tra dữ liệu nhập vào
    private boolean validateInput() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Kiểm tra họ và tên không được để trống
        if (firstName.isEmpty()) {
            etFirstName.setError("Họ không được để trống");
            return false;
        }
        if (lastName.isEmpty()) {
            etLastName.setError("Tên không được để trống");
            return false;
        }

        // Kiểm tra định dạng email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email không hợp lệ");
            return false;
        }

        // Kiểm tra số điện thoại (chỉ chấp nhận số và tối thiểu 10 chữ số)
        if (!phoneNumber.matches("\\d{10,11}")) {
            etPhoneNumber.setError("Số điện thoại không hợp lệ");
            return false;
        }

        // Kiểm tra username (phải có ít nhất 4 ký tự)
        if (userName.length() < 4) {
            etUserName.setError("Tên đăng nhập phải có ít nhất 4 ký tự");
            return false;
        }

        // Kiểm tra mật khẩu (ít nhất 6 ký tự)
        if (password.length() < 1) {
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }

        return true; // Nếu mọi thứ hợp lệ
    }
    public void resetFields() {
        etFirstName.setText("");
        etLastName.setText("");
        etEmail.setText("");
        etPhoneNumber.setText("");
        etUserName.setText("");
        etPassword.setText("");
    }
}