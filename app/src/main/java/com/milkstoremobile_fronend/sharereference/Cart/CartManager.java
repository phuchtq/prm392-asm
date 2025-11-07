package com.milkstoremobile_fronend.sharereference.Cart;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.milkstoremobile_fronend.models.cart.Cart;
import com.milkstoremobile_fronend.models.cart.ProductDetailOfCart;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String PREF_NAME = "CART_PREF";
    private static final String KEY_CART = "CART_DATA";
    private static CartManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    private CartManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }
        return instance;
    }

    // Lưu giỏ hàng vào SharedPreferences
    public void saveCart(Cart cart) {
        String json = gson.toJson(cart);
        editor.putString(KEY_CART, json);
        editor.apply();
    }

    // Lấy giỏ hàng từ SharedPreferences
    public Cart getCart() {
        String json = sharedPreferences.getString(KEY_CART, null);
        if (json == null) return new Cart("", "", new ArrayList<>());
        Type type = new TypeToken<Cart>() {}.getType();
        return gson.fromJson(json, type);
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addProductToCart(ProductDetailOfCart product) {
        Cart cart = getCart();
        List<ProductDetailOfCart> orderDetails = cart.getOrderDetails();

        boolean productExists = false;

        for (ProductDetailOfCart item : orderDetails) {
            if (item.getProductId().equals(product.getProductId())) {
                // Nếu sản phẩm đã tồn tại, tăng số lượng
                item.setQuantity(item.getQuantity() + product.getQuantity());
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            // Nếu sản phẩm chưa có, thêm mới vào giỏ hàng
            orderDetails.add(product);
        }

        saveCart(cart);
    }

    // Xóa giỏ hàng
    public void clearCart() {
        editor.remove(KEY_CART);
        editor.apply();
    }
}
