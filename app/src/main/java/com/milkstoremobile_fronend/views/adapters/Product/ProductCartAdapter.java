package com.milkstoremobile_fronend.views.adapters.Product;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Product;
import com.milkstoremobile_fronend.models.cart.ProductDetailOfCart;

import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ProductViewHolder> {
    private List<ProductDetailOfCart> productList;
    private Context context;

    public ProductCartAdapter(List<ProductDetailOfCart> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_cart, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductDetailOfCart product = productList.get(position);

        holder.tvProductName.setText(product.getProductName());
        holder.tvProductPrice.setText("Giá: " + product.getPrice() + "đ");
        holder.tvProductQuantity.setText("x" + product.getQuantity());
        holder.tvProductDescription.setText(product.getDescription());
        holder.tvProductStatus.setText(product.getStatusDescription());

        Log.d("MilkAdapter", "desc=" + product.getDescription() + ", status=" + product.getStatusDescription());

        Glide.with(context)
                .load(product.getImage())
                .placeholder(R.drawable.icon_product)
                .into(holder.imgProduct);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvProductPrice, tvProductQuantity, tvProductDescription, tvProductStatus;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            tvProductStatus = itemView.findViewById(R.id.tvProductStatus);
        }
    }

    // Cập nhật danh sách sản phẩm
    public void updateProductList(List<ProductDetailOfCart> newProductList) {
        this.productList = newProductList;
        notifyDataSetChanged();
    }
}