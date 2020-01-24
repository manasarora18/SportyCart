package com.project.sportycart.adapter;

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

import com.project.sportycart.R;
import com.project.sportycart.ViewCartActivity;
import com.project.sportycart.entity.Cart;
import com.project.sportycart.entity.Product;
import com.project.sportycart.retrofit.GetCartApis;

import java.util.List;

import retrofit2.Call;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartList;
    public ICartCommunicator iCartCommunicator;

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
        //Glide.with(cartViewHolder.imageView.getContext()).load(dataItem.getImageUrl().toString()).into(cartViewHolder.imageView);

        cartViewHolder.textView.setText("Product ID: " + dataItem.getProductId()
                + "\nPrice: " + dataItem.getPrice()
                + "\nMerchant: " + dataItem.getMerchantId());

        cartViewHolder.incrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(cartViewHolder.quantityText.getText().toString()) >= 0) {

                    cartViewHolder.deductQuantity.setEnabled(true);

                }
                cartViewHolder.quantityText.setText(String.valueOf(Integer.parseInt(cartViewHolder.quantityText.getText().toString()) + 1));
                dataItem.setQuantity(Integer.parseInt(cartViewHolder.quantityText.getText().toString()));
                iCartCommunicator.updateQuantity(dataItem.getProductId(), Integer.parseInt(cartViewHolder.quantityText.getText().toString()));
            }
        });


        cartViewHolder.deductQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(cartViewHolder.quantityText.getText().toString()) == 1) {
                    cartViewHolder.deductQuantity.setEnabled(false);

                    int model = cartViewHolder.getAdapterPosition();
                    cartList.remove(model);
                    notifyItemChanged(model);
                    System.out.println("check "+dataItem.getMerchantId()+" "+dataItem.getProductId());
                    iCartCommunicator.removeFromCart(dataItem.getMerchantId(), dataItem.getProductId());


                } else {
                    cartViewHolder.deductQuantity.setEnabled(true);
                    cartViewHolder.quantityText.setText(String.valueOf(Integer.parseInt(cartViewHolder.quantityText.getText().toString()) - 1));
                    dataItem.setQuantity(Integer.parseInt(cartViewHolder.quantityText.getText().toString()));
                    iCartCommunicator.updateQuantity(dataItem.getProductId(), Integer.parseInt(cartViewHolder.quantityText.getText().toString()));
                }

            }
        });
        cartViewHolder.quantityText.setText(String.valueOf(dataItem.getQuantity()));

        //deleteCartItem = findViewById(R.id.removeCartItem);
        cartViewHolder.deleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int model = cartViewHolder.getAdapterPosition();
                cartList.remove(model);
                notifyItemChanged(model);
                iCartCommunicator.removeFromCart(dataItem.getMerchantId(), dataItem.getProductId());
                //iCartCommunicator.removeFromCart(dataItem.getMerchantId(),dataItem.getProductId());
                //v.setVisibility(View.INVISIBLE);
            }
        });

    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public interface ICartCommunicator {
        boolean updateQuantity(String productId, int quantity);

        boolean removeFromCart(String merchantId, String productId);
    }
}