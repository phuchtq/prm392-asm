package com.milkstoremobile_fronend.views.adapters.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.milkstoremobile_fronend.models.order.Order;
import com.milkstoremobile_fronend.R;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private List<Order> orderList;
    private OnOrderStatusChangeListener listener;

    public interface OnOrderStatusChangeListener {
        void onStatusChange(Order order);
    }

    public OrderAdapter(Context context, OnOrderStatusChangeListener listener) {
        this.context = context;
        this.listener = listener;
        this.orderList = new ArrayList<>();
    }

    public void setOrders(List<Order> newOrders) {
        if (newOrders != null) {
            this.orderList.clear();
            this.orderList.addAll(newOrders);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_admin, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.tvOrderId.setText("Mã đơn hàng: " + order.getOrderId());
        holder.tvCustomerId.setText("Mã khách hàng: " + order.getCustomerId());
        holder.tvTotal.setText("Tổng tiền: " + order.getTotal() + " $");
        holder.tvShippingAddress.setText("Địa chỉ: " + order.getShippingAddress());
        holder.tvOrderStatus.setText("Trạng thái: " + order.getOrderStatus());

        holder.btnChangeStatus.setOnClickListener(v -> {
            if (listener != null) {
                listener.onStatusChange(order);
            }
        });

        // Ẩn nút nếu trạng thái đơn hàng đã giao
        if ("Delivered".equalsIgnoreCase(order.getOrderStatus())) {
            holder.btnChangeStatus.setVisibility(View.GONE);
        } else {
            holder.btnChangeStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvCustomerId, tvTotal, tvShippingAddress, tvOrderStatus;
        Button btnChangeStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvCustomerId = itemView.findViewById(R.id.tvCustomerId);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvShippingAddress = itemView.findViewById(R.id.tvShippingAddress);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            btnChangeStatus = itemView.findViewById(R.id.btnChangeStatusOrder);
        }
    }
}
