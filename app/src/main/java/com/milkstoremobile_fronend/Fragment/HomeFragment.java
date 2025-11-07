package com.milkstoremobile_fronend.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Product;
import com.milkstoremobile_fronend.models.cart.ProductDetailOfCart;
import com.milkstoremobile_fronend.viewmodels.ProductViewModel;
import com.milkstoremobile_fronend.views.activitys.MainActivity;
import com.milkstoremobile_fronend.views.activitys.OrderActivity.OrderActivity;
import com.milkstoremobile_fronend.views.activitys.cart.CartActivity;
import com.milkstoremobile_fronend.views.adapters.Product.ProductCartAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewPopularProducts;
    private ProductViewModel productViewModel;
    private ProductCartAdapter productCartAdapter;
    private ImageButton btnOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        btnOrder = view.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(e -> navigateToViewOrder());

        observeProductData();

        recyclerViewPopularProducts = view.findViewById(R.id.recyclerViewPopularProducts);
        recyclerViewPopularProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        productCartAdapter = new ProductCartAdapter(new ArrayList<>(), getContext());
        recyclerViewPopularProducts.setAdapter(productCartAdapter);



        return view;
    }

    private void observeProductData() {
        productViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            if (products != null && !products.isEmpty()) {
                List<ProductDetailOfCart> cartItems = new ArrayList<>();

                for (Product p : products) {
                    ProductDetailOfCart item = new ProductDetailOfCart(
                            p.getId(),
                            p.getProductName(),
                            p.getQuantity(),
                            p.getPrice(),
                            p.getImage(),
                            p.getDescription(),
                            p.getStatusDescription()
                    );
                    cartItems.add(item);
                }

                Log.d("HomeFragment", "Tổng số sản phẩm truyền vào adapter: " + cartItems.size());
                productCartAdapter.updateProductList(cartItems);
            } else {
                Log.d("HomeFragment", "Không có sản phẩm nào được nhận từ ViewModel.");
            }
        });
    }

    private void navigateToViewOrder() {
        Intent intent = new Intent(requireContext(), OrderActivity.class);
        startActivity(intent);
    }
}


