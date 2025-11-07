package com.milkstoremobile_fronend.views.adapters.Product;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.milkstoremobile_fronend.models.cart.ProductDetailOfCart;
import com.milkstoremobile_fronend.sharereference.Cart.CartManager;
import com.milkstoremobile_fronend.views.activitys.cart.CartActivity;
import com.milkstoremobile_fronend.views.activitys.milk.MilkDetailActivity;
import com.milkstoremobile_fronend.models.Product;
import com.milkstoremobile_fronend.R;

import java.util.ArrayList;
import java.util.List;

public class MilkAdapter extends RecyclerView.Adapter<MilkAdapter.MilkViewHolder> {
    private Context context;
    private List<Product> milkList;
    private boolean isCategoryMode; // Kiểm tra đang ở CategoryFragment hay MilkFragment
    private static List<Product> cartList = new ArrayList<>(); // Danh sách giỏ hàng
    private List<Product> originalList = new ArrayList<>();

    public MilkAdapter(Context context, List<Product> productList, boolean isCategoryMode) {
        this.context = context;
        this.milkList = productList;
        this.originalList = new ArrayList<>(productList); // Lưu list gốc
        this.isCategoryMode = isCategoryMode;
    }

    @NonNull
    @Override
    public MilkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_milk, parent, false);
        return new MilkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkViewHolder holder, int position) {
        Product milk = milkList.get(position);

        holder.milkName.setText(milk.getProductName());
        holder.milkPrice.setText("$" + milk.getPrice());
        Glide.with(context).load(milk.getImage()).into(holder.milkImage);

        if (isCategoryMode) {
            // Nếu ở CategoryFragment, nút là "Add to Cart"
            holder.btnAction.setText("Thêm vô giỏ hàng");
            holder.btnAction.setOnClickListener(v -> {
                if (milk.getId() == null) {
                    Toast.makeText(context, "Lỗi: ID sản phẩm bị null!", Toast.LENGTH_SHORT).show(); // ✅ Dùng context
                    return;
                }

                ProductDetailOfCart productDetail = new ProductDetailOfCart(
                        milk.getId(),
                        milk.getProductName(),
                        1,
                        milk.getPrice(),
                        milk.getImage(),
                        milk.getDescription(),
                        "Available"
                );

                CartManager.getInstance(context).addProductToCart(productDetail); // ✅ Dùng context
                Toast.makeText(context, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            });
        } else {

            // Nếu ở MilkFragment, nút là "Detail"
            holder.btnAction.setText("Chi tiết");
            holder.btnAction.setOnClickListener(v -> {
                Log.d("MilkAdapter", "Milk received: " + milk);
                Intent intent = new Intent(context, MilkDetailActivity.class);
                intent.putExtra("milk", milk);
                context.startActivity(intent);
            });
        }
    }

    public void updateData(List<Product> newMilkList) {
        this.milkList = newMilkList;
        this.originalList = new ArrayList<>(newMilkList); // Nếu muốn reset list gốc
        notifyDataSetChanged();
    }

    public void filterList(List<Product> filteredList) {
        this.milkList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return milkList.size();
    }

    public static List<Product> getCartList() {
        return cartList;
    }

    public List<Product> getAllProducts() {
        return originalList;
    }

    public static class MilkViewHolder extends RecyclerView.ViewHolder {
        ImageView milkImage;
        TextView milkName, milkPrice;
        Button btnAction;

        public MilkViewHolder(@NonNull View itemView) {
            super(itemView);
            milkImage = itemView.findViewById(R.id.imgMilk);
            milkName = itemView.findViewById(R.id.tvMilkName);
            milkPrice = itemView.findViewById(R.id.tvMilkPrice);
            btnAction = itemView.findViewById(R.id.btnAction); // Đổi ID từ btnDetail thành btnAction
        }
    }


}
