package com.project.sportycart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.project.sportycart.ProductCollection;
import com.project.sportycart.R;
import com.project.sportycart.ViewCartActivity;
import com.project.sportycart.entity.Cart;
import com.project.sportycart.entity.Product;
import com.project.sportycart.retrofit.GetCartApis;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Cart> cartList;
    public ICartCommunicator iCartCommunicator;
    private List<Product> cartProductList;

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public TextView quantityText;
        public ImageButton deductQuantity;
        public ImageButton incrementQuantity;
        public ImageButton deleteCartItem;
        public Button confirmOrder;
        int quantity = 1;

        public CartViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.cartProductDescription);
            imageView = v.findViewById(R.id.cartProductImage);
            quantityText = v.findViewById(R.id.quantityText);
            deductQuantity = v.findViewById(R.id.deductCartQuantity);
            incrementQuantity = v.findViewById(R.id.incrementCartQuantity);
            confirmOrder = v.findViewById(R.id.confirmOrderButton);
            deleteCartItem = v.findViewById(R.id.removeCartItem);
        }
    }

    public CartAdapter(List<Cart> myList, ICartCommunicator iCartCommunicator) {
        cartList = myList;
        this.iCartCommunicator = iCartCommunicator;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        CartViewHolder cartViewHolder = new CartViewHolder(v);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(final CartViewHolder cartViewHolder, int position) {
        final Cart dataItem = cartList.get(position);

        //fetching all products
        cartProductList = ProductCollection.get();
        Product cartProduct = new Product();
        for (Product product : cartProductList) {
            if (product.getProductId().equals(dataItem.getProductId()))
                cartProduct = product;
        }
        System.out.println(cartProduct.getImageUrl());
        //Image of product
        Picasso.with(cartViewHolder.imageView.getContext()).load(cartProduct.getImageUrl()).resize(200, 200).centerCrop().into(cartViewHolder.imageView);
        cartViewHolder.textView.setText(cartProduct.getName()
                + "\nRs: " + dataItem.getPrice()
                + "\nMerchant: " + dataItem.getMerchantId());

        //Cart quantity increment-decrement buttons
        cartViewHolder.incrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(cartViewHolder.quantityText.getText().toString()) >= 0) {
                    cartViewHolder.deductQuantity.setEnabled(true);
                }
                cartViewHolder.quantityText.setText(String.valueOf(Integer.parseInt(cartViewHolder.quantityText.getText().toString()) + 1));
                dataItem.setQuantity(Integer.parseInt(cartViewHolder.quantityText.getText().toString()));
                iCartCommunicator.updateQuantity(dataItem.getProductId(), Integer.parseInt(cartViewHolder.quantityText.getText().toString()),dataItem.getMerchantId());
            }
        });
        cartViewHolder.deductQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(cartViewHolder.quantityText.getText().toString()) == 1) {
                    cartViewHolder.deductQuantity.setEnabled(false);
                    int prevSize = getItemCount();
                    int model = cartViewHolder.getAdapterPosition();
                    cartList.remove(model);
                    notifyItemChanged(model);
                    notifyItemRangeChanged(0, getItemCount() + 1);
                    notifyItemRangeRemoved(getItemCount() + 1, prevSize);
                    System.out.println("check " + dataItem.getMerchantId() + " " + dataItem.getProductId());
                    iCartCommunicator.removeFromCart(dataItem.getMerchantId(), dataItem.getProductId());
                } else {
                    cartViewHolder.deductQuantity.setEnabled(true);
                    cartViewHolder.quantityText.setText(String.valueOf(Integer.parseInt(cartViewHolder.quantityText.getText().toString()) - 1));
                    dataItem.setQuantity(Integer.parseInt(cartViewHolder.quantityText.getText().toString()));
                    iCartCommunicator.updateQuantity(dataItem.getProductId(), Integer.parseInt(cartViewHolder.quantityText.getText().toString()),dataItem.getMerchantId());
                }
            }
        });

        //set quantity
        cartViewHolder.quantityText.setText(String.valueOf(dataItem.getQuantity()));

        //delete item from cart
        cartViewHolder.deleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prevSize = getItemCount();
                int model = cartViewHolder.getAdapterPosition();
                cartList.remove(model);
                notifyItemChanged(model);
                notifyItemRangeChanged(0, getItemCount() + 1);
                notifyItemRangeRemoved(getItemCount() + 1, prevSize);

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public interface ICartCommunicator {
        boolean updateQuantity(String productId, int quantity,String merchantId);

        boolean removeFromCart(String merchantId, String productId);
    }
}