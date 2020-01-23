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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    List<Product> searchList;
    private Context context;
    private ProductCommunication productCommunication;

    public SearchAdapter(List<Product>searchList, ProductCommunication productCommunication){
//        this.context=context;
        this.searchList=searchList;
        this.productCommunication=productCommunication;
    }
    public class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView productName;
        ImageView productImage;

        public SearchViewHolder(View itemView){
            super(itemView);
            this.productName=itemView.findViewById((R.id.productName));
            this.productImage=itemView.findViewById(R.id.productImage);
        }
    }
    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_items,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, final int position) {

        holder.productName.getRootView() .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product=searchList.get(position);
                if(product!=null) {
                    productCommunication.onClick(searchList.get(position));
                }
                System.out.println("NULL IN SEARCH");
            }
        });
        holder.productName.setText(searchList.get(position).getName());
        Glide.with(holder.productImage.getContext())
                .load(searchList.get(position).getImageUrl())
                .into(holder.productImage);
//        holder.productPrice.setText(productList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        if (searchList != null) {
            return searchList.size();
        }
            return 0;
    }

    public interface ProductCommunication {
        void onClick(Product product);
    }
}
