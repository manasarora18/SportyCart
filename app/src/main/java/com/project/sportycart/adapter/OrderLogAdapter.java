package com.project.sportycart.adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.sportycart.ProductCollection;
import com.project.sportycart.R;
import com.project.sportycart.entity.OrderTable;
import com.project.sportycart.entity.Product;

import java.util.List;


public class OrderLogAdapter extends RecyclerView.Adapter<OrderLogAdapter.OrderViewHolder> {
    static List<OrderTable> list;
    public IOrderCommunicator iOrderCommunicator;
    private SharedPreferences sharedPreferences;


    public OrderLogAdapter(List<OrderTable> List, IOrderCommunicator iOrderCommunicator) {
        this.list = List;
        this.iOrderCommunicator = iOrderCommunicator;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.order_recycle_list, parent, false);
        OrderViewHolder viewHolder = new OrderViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, final int position) {
        final OrderTable orderTable = list.get(position);
        holder.orderId.setText(list.get(position).getOrderId());

        //holder.rating.setText( String.valueOf(list.get(position).getRating()) );
        holder.ratingBar.setRating(Float.parseFloat(String.valueOf(list.get(position).getRating())));
        holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Toast toast = Toast.makeText(this, String.valueOf(rating), Toast.LENGTH_SHORT);
                //toast.show();
                rating = holder.ratingBar.getRating();
//                if(orderTable.getRating()>0)
//                {
//                    //holder.ratingBar.setIsIndicator(true);
//                    holder.submitRating.setVisibility(View.INVISIBLE);
//                   // holder.ratingBar.getNumStars();
//                }
                orderTable.setRating(rating);
                //holder.ratingBar.setRating(rating);
                System.out.println(orderTable.getRating());

            }
        });
        //Product product=new Product();
        final List<Product> productList = ProductCollection.get();
        for (Product product : productList) {
            if (product.getProductId().equals(list.get(position).getProductId())) {
                holder.productName.setText(product.getName());
            }
        }
        //holder.productName.setText(list.get(position).);
        holder.price.setText(list.get(position).getPrice());
        holder.quantity.setText(String.valueOf(list.get(position).getQuantity()));
        System.out.println(orderTable.getRating());

        holder.merchantId.setText(list.get(position).getMerchantId());
        //holder.timeStamp.setText(list.get(position).getTimeStamp());

//        String userId = sharedPreferences.getString("UserId", "");
        holder.submitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.ratingBar.getRating();
                holder.ratingBar.setIsIndicator(true);
                //holder.ratingBar.getNumStars();
                System.out.println("Submit:" + orderTable.getRating());
                //holder.ratingBar.setRating();
                iOrderCommunicator.setRating(orderTable.getOrderId(), orderTable.getProductId(), orderTable.getMerchantId(), orderTable.getUserId(), orderTable.getRating());
                holder.submitRating.setVisibility(View.INVISIBLE);

            }
        });


    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView orderId;
        public RatingBar ratingBar;
        public TextView productName;
        public TextView price;
        public TextView quantity;
        public TextView merchantId;
        public TextView timeStamp;
        public Button submitRating;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = (TextView) itemView.findViewById(R.id.orderId);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            price = (TextView) itemView.findViewById(R.id.price);
            productName = (TextView) itemView.findViewById(R.id.productName);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            merchantId = (TextView) itemView.findViewById(R.id.merchantId);
            timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);
            submitRating = (Button) itemView.findViewById(R.id.submitRating);
        }
    }

    public interface IOrderCommunicator {
        boolean setRating(String orderId, String productId, String merchantId, String userId, double rating);


    }
}