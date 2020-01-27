package com.project.sportycart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.sportycart.R;
import com.project.sportycart.entity.Product;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private List<Product> productList;
    private ProductCommunication productCommunication;

    public HomeAdapter(List<Product> productList, ProductCommunication productCommunication) {
        this.productList = productList;
        this.productCommunication = productCommunication;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        ImageView productImage;

        public HomeViewHolder(View itemView) {
            super(itemView);
            this.productName = itemView.findViewById((R.id.productName));
            this.productImage = itemView.findViewById(R.id.productImage);
        }
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_items, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, final int position) {
        holder.productName.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productCommunication.onClick(productList.get(position));
            }
        });
        holder.productName.setText(productList.get(position).getName());

        if (productList.get(position).getImageUrl() != null) {
            Glide.with(holder.productImage.getContext())
                    .load(productList.get(position).getImageUrl())
                    .into(holder.productImage);
        }
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    public interface ProductCommunication {
        void onClick(Product product);
    }
}