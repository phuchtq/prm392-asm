package com.milkstoremobile_fronend.views.activitys.product;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Product;
import com.milkstoremobile_fronend.viewmodels.ProductViewModel;
import com.milkstoremobile_fronend.views.adapters.Product.ProductAdapter;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    private ProductViewModel productViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton btnAddProduct;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recycler_view_product);
        progressBar = findViewById(R.id.progress_bar_product);
        btnAddProduct = findViewById(R.id.btn_add_product);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(this, productList, this);
        recyclerView.setAdapter(productAdapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getProducts().observe(this, this::updateProductList);
        productViewModel.getIsLoading().observe(this, this::toggleLoading);

        btnAddProduct.setOnClickListener(v -> openAddProductActivity());
    }

    private void updateProductList(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        productAdapter.notifyDataSetChanged();
    }

    private void toggleLoading(Boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void openAddProductActivity() {
        Intent intent = new Intent(this, ProductFormActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEditProduct(Product product) {
        Intent intent = new Intent(this, ProductFormActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    public void onDeleteProduct(Product product) {
        productViewModel.deleteProduct(product.getId());
    }
}
