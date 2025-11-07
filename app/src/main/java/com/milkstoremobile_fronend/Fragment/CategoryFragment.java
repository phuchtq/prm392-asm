package com.milkstoremobile_fronend.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Product;
import com.milkstoremobile_fronend.viewmodels.ProductViewModel;
import com.milkstoremobile_fronend.views.adapters.Product.MilkAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private RecyclerView recyclerViewFilteredMilk;
    private List<Product> filteredMilkList;
    private MilkAdapter milkAdapter;
    private ProductViewModel productViewModel;
    private ChipGroup chipGroupPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        chipGroupPrice = view.findViewById(R.id.chipGroupPrice);
        recyclerViewFilteredMilk = view.findViewById(R.id.recyclerViewFilteredMilk);

        // ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Adapter sữa
        filteredMilkList = new ArrayList<>();
        milkAdapter = new MilkAdapter(getContext(), filteredMilkList, true);
        recyclerViewFilteredMilk.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFilteredMilk.setAdapter(milkAdapter);

        // Lắng nghe chọn chip
        chipGroupPrice.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds == null || checkedIds.isEmpty()) return;

            int checkedId = checkedIds.get(0); // Vì bạn dùng singleSelection nên luôn chỉ có 1 ID

            if (checkedId == R.id.chipUnder200) {
                filterMilkByPrice("Dưới 200.000₫");
            } else if (checkedId == R.id.chip200to500) {
                filterMilkByPrice("200.000₫ - 500.000₫");
            } else if (checkedId == R.id.chip500to1m) {
                filterMilkByPrice("500.000₫ - 1.000.000₫");
            }
        });




        // Lấy dữ liệu từ ViewModel
        productViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                filterMilkByPrice("200.000₫ - 500.000₫"); // mặc định
                chipGroupPrice.check(R.id.chip200to500); // tự chọn chip mặc định
            }
        });

        if (productViewModel.getProducts().getValue() == null) {
            productViewModel.fetchProducts();
        }

        return view;
    }

    private void filterMilkByPrice(String priceRange) {
        filteredMilkList.clear();
        List<Product> allProducts = productViewModel.getProducts().getValue();
        if (allProducts == null) {
            Log.e("CategoryFragment", "allProducts is null");
            return;
        }

        for (Product milk : allProducts) {
            if (milk == null) continue;
            double price = milk.getPrice();

            if (priceRange.equals("Dưới 200.000₫") && price < 200000) {
                filteredMilkList.add(milk);
            } else if (priceRange.equals("200.000₫ - 500.000₫") && price >= 200000 && price <= 500000) {
                filteredMilkList.add(milk);
            } else if (priceRange.equals("500.000₫ - 1.000.000₫") && price > 500000 && price <= 1000000) {
                filteredMilkList.add(milk);
            }
        }

        milkAdapter.notifyDataSetChanged();
    }
}
