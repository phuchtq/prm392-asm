package com.milkstore.views.adapters.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.milkstore.R;
import com.milkstore.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> products;
    private OnProductClickListener listener;

    public ProductAdapter(Context context, List<Product> products, OnProductClickListener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        this.listener = null;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvName.setText(product.getProductName());
        holder.tvPrice.setText(String.format("%.0f₫", product.getPrice()));

        Glide.with(context)
                .load(product.getImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.imgProduct);

        // Bắt sự kiện click nếu listener tồn tại
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditProduct(product); // hoặc mở chi tiết tùy bạn dùng
            }
        });

        // Nếu có nút xóa/sửa thì có thể thêm onDeleteProduct, ví dụ:
        // holder.btnDelete.setOnClickListener(v -> listener.onDeleteProduct(product));
    }

    public void updateProductList(List<Product> newProductList) {
        this.products = newProductList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // ✅ ViewHolder duy nhất
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.txtProductName);
            tvPrice = itemView.findViewById(R.id.txtProductPrice);
        }


    }

    // ✅ Interface click
    public interface OnProductClickListener {
        void onEditProduct(Product product);
        void onDeleteProduct(Product product);
    }
}
