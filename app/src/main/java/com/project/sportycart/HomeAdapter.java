package com.project.sportycart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private List<Product> productList;
    private Context context;
    public HomeAdapter(Context context, List<Product>productList){
        this.context=context;
        this.productList=productList;
    }
    public class HomeViewHolder extends RecyclerView.ViewHolder{
        TextView textView1;
        ImageView imageView;

        public HomeViewHolder(View itemView){
            super(itemView);

            textView1=itemView.findViewById((R.id.textView1));
            imageView=itemView.findViewById(R.id.coverImage);
        }
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_items,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder,int position) {
    holder.textView1.setText(productList.get(position).getName());

//    Glide.with(this.context)
//            .load(productList.get(position).getImageUrl())
//            .into(holder.imageView);
//        holder.textView2.setText(productList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}
