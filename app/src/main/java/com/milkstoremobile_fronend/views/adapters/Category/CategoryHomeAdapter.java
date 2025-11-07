package com.milkstoremobile_fronend.views.adapters.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.models.Category;

import java.util.List;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.ViewHolder> {

    private final List<Category> categoryList;
    private final Context context;

    public CategoryHomeAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.txtCategoryName.setText(category.getBrandName());

        // Gắn ảnh theo tên thương hiệu
        int imageResId = getImageResourceByBrandName(category.getBrandName());
        holder.imgCategory.setImageResource(imageResId);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoryName;
        ImageView imgCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            imgCategory = itemView.findViewById(R.id.imgCategory);
        }
    }

    // Hàm gắn ảnh local theo brandName
    private int getImageResourceByBrandName(String brandName) {
        if (brandName == null) return R.drawable.placeholder; // fallback nếu null

        switch (brandName.trim().toLowerCase()) {
            case "vinamilk":
                return R.drawable.vinamilk_logo;
            case "th true milk":
                return R.drawable.th_true_milk_logo;
            case "meiji_logo":
                return R.drawable.meiji_logo;

            case "abbott":
                return R.drawable.abbott_logo;
            case "nutifood":
                return R.drawable.nutifood_logo;
            default:
                return R.drawable.placeholder; // fallback nếu không khớp
        }
    }
}
