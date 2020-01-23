package com.project.sportycart.adapter;

import android.content.Context;
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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<Product> productList;
    private Context context;
    private ProductCommunication productCommunication;

    public CategoryAdapter(List<Product>productList, ProductCommunication productCommunication){
        this.productList=productList;
        this.productCommunication=productCommunication;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView productName;
        ImageView productImage;

        public CategoryViewHolder(View itemView){
            super(itemView);
            this.productName=itemView.findViewById((R.id.productName));
            this.productImage=itemView.findViewById(R.id.productImage);
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_items,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder,final int position) {

        holder.productName.getRootView() .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = productList.get(position);
                if (product != null) {
                    productCommunication.onClick(productList.get(position));
                }
                else {
                    System.out.println("NULL in category");
                }
            }
        });

        Product product=productList.get(position);
        if(product!=null) {
            holder.productName.setText(product.getName());
        }
        else {
            System.out.println("NULL FOUND");
        }

        Glide.with(holder.productImage.getContext())
                .load(productList.get(position).getImageUrl())
                .into(holder.productImage);
//        holder.productPrice.setText(productList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        if(productList != null) {
            return productList.size();
        }
            return 0;
    }

    public interface ProductCommunication{
        void onClick(Product product);
    }
}