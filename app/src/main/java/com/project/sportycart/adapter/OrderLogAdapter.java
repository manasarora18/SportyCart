package com.project.sportycart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.sportycart.R;
import com.project.sportycart.entity.OrderTable;
import java.util.List;

public class OrderLogAdapter extends RecyclerView.Adapter<OrderLogAdapter.OrderViewHolder> {
    static List<OrderTable> list;
    public OrderLogAdapter(List<OrderTable> List){
        this.list = List;
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.recycle_list, parent, false);
        OrderViewHolder viewHolder = new OrderViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.orderId.setText(list.get(position).getOrderId());
        holder.productId.setText(list.get(position).getProductId());
        holder.price.setText(list.get(position).getPrice());
        holder.quantity.setText(""+list.get(position).getQuantity());
        holder.merchantId.setText(list.get(position).getMechantId());
        holder.timeStamp.setText(list.get(position).getTimeStamp());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView orderId;
        public TextView productId;
        public TextView price;
        public TextView quantity;
        public TextView merchantId;
        public TextView timeStamp;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = (TextView) itemView.findViewById(R.id.orderId);
            productId = (TextView) itemView.findViewById(R.id.productId);
            price = (TextView) itemView.findViewById(R.id.price);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            merchantId = (TextView) itemView.findViewById(R.id.merchantId);
            timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);
        }
    }
}