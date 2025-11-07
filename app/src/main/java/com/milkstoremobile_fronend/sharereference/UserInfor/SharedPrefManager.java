package com.milkstoremobile_fronend.sharereference.UserInfor;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.milkstoremobile_fronend.models.Login.LoginResponse;

public class SharedPrefManager {
    private static final String PREF_NAME = "app_prefs";
    private static final String KEY_USER = "user_data";

    private static SharedPrefManager instance;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    // Lưu context ở instance, không dùng static context để tránh memory leak
    private SharedPrefManager(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    // Lưu object LoginResponse
    public void saveUser(LoginResponse user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(user);
        editor.putString(KEY_USER, json);
        editor.apply();
    }

    // Lấy object LoginResponse
    public LoginResponse getUser() {
        String json = sharedPreferences.getString(KEY_USER, null);
        return json != null ? gson.fromJson(json, LoginResponse.class) : null;
    }

    // ✅ Lấy userId từ object LoginResponse đã lưu
    public String getUserId() {
        LoginResponse user = getUser();
        return (user != null) ? user.getCustomerId() : null;
    }

    // Xóa dữ liệu khi logout
    public void clearUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER);
        editor.apply();
    }
}
