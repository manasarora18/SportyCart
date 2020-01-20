package com.project.sportycart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<RawData> cartList;

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public CartViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.cartProductDescription);
            imageView = v.findViewById(R.id.cartProductImage);
        }
    }

    public CartAdapter(List<RawData> myList) {
        cartList = myList;
    }

    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        CartViewHolder cartViewHolder = new CartViewHolder(v);
        return cartViewHolder;

    }

    @Override
    public void onBindViewHolder(CartViewHolder cartViewHolder, int position) {
        //cartList.get(position);
        RawData dataItem = cartList.get(position);
        Glide.with(cartViewHolder.imageView.getContext()).load(dataItem.getUrl().getMedium()).into(cartViewHolder.imageView);
        cartViewHolder.textView.setText(" "+dataItem.getName()+"\n"+dataItem.getTimestamp());
        //LinearLayout linearLayout = (LinearLayout) cartViewHolder.imageView.getRootView();


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

}
