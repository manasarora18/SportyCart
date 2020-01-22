package com.project.sportycart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.sportycart.R;
import com.project.sportycart.entity.Product;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Product> productList;
    private Context context;
    private HomeAdapter.ProductCommunication productCommunication;

    public CategoryAdapter(Context context,List<Product>productList){
        this.context=context;
        this.productList=productList;

    }
    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.activity_category_products,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        holder.productName.setText(productList.get(position).getName());

//    Glide.with(this.context)
//            .load(productList.get(position).getImageUrl())
//            .into(holder.productImage);
//        holder.productPrice.setText(productList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView productName;
        ImageView productImage;

        public CategoryViewHolder(View itemView){
            super(itemView);

            productName=itemView.findViewById((R.id.productName));
            productImage=itemView.findViewById(R.id.productImage);
        }
    }

    public interface ProductDescription{
        void onClick(Product product);
    }
}
