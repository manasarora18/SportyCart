package com.project.sportycart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.sportycart.R;
import com.project.sportycart.entity.Cart;
import com.project.sportycart.entity.MerchantDetails;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List<MerchantDetails> merchantDetailsList;
    private MerchantDetails merchantDetails;
    private Cart cart;
    private MerchantDetailsInterface merchantDetailsInterface;

    public ProductAdapter(List<MerchantDetails>merchantDetailsList,MerchantDetailsInterface merchantDetailsInterface){
        this.merchantDetailsList=merchantDetailsList;
        this.merchantDetailsInterface=merchantDetailsInterface;
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView merchantName;
        TextView priceDetails;
        Button addToCart;

        public ProductViewHolder(View itemView){
            super(itemView);
            this.merchantName=itemView.findViewById(R.id.merchantName);
            this.priceDetails=itemView.findViewById(R.id.priceDetails);
            this.addToCart=itemView.findViewById(R.id.addToCart);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.merc_product_items,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        holder.merchantName.setText(merchantDetailsList.get(position).getMerchantId());
        holder.priceDetails.setText(String.valueOf(merchantDetailsList.get(position).getPrice()));
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchantDetailsInterface.onClick(merchantDetailsList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(merchantDetailsList!=null){
            return merchantDetailsList.size();
        }
        return 0;
    }

    public interface MerchantDetailsInterface{
        void onClick(MerchantDetails merchantDetails);
    }
}