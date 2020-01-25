package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.sportycart.entity.Cart;
import com.project.sportycart.entity.Order;
import com.project.sportycart.entity.OrderTable;
import com.project.sportycart.entity.StockCheckDTO;
import com.project.sportycart.retrofit.GetOrderApis;
import com.project.sportycart.retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    List<Cart> orderList;
    SharedPreferences sharedPreferences;
    GetOrderApis getOrderApis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        saveOrderandCheckOut();
        //addRating();
        redirectToHome();
        TextView orderPlacedText = findViewById(R.id.orderPlacedText);
        orderPlacedText.setText("Order Your has been placed successfully and will be delivered to you shortly," +
                "\nThank you for choosing SportyCart!!!");
    }

    //Call to save order API
    void saveOrderandCheckOut() {
        GetOrderApis getOrderApis;
        getOrderApis = RetrofitClientInstance.getRetrofitInstance().create(GetOrderApis.class);
        orderList = CartCollection.get();
        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", "");
        Call<List<StockCheckDTO>> callCheckoutOrder = getOrderApis.checkoutOrder(orderList, "75");//userId
        callCheckoutOrder.enqueue(new Callback<List<StockCheckDTO>>() {
            @Override
            public void onResponse(Call<List<StockCheckDTO>> call, Response<List<StockCheckDTO>> response) {
                System.out.println("Inside Checkout onResponse");
            }

            @Override
            public void onFailure(Call<List<StockCheckDTO>> call, Throwable t) {
                System.out.println("Inside Checkout onFailure");
            }
        });

        //for(OrderTable orderTable:)
    }

//    void addRating() {
//        //Rating bar
//        RatingBar ratingBar = findViewById(R.id.ratingBar);
//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(rating), Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
//    }

    void redirectToHome() {
        //Button redirecting to home page
        ImageButton backToHome = (ImageButton) findViewById(R.id.imageButton);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });
//        Button submitButton = findViewById(R.id.ratingSubmitButton);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //API to save the ratings
//
//                //Call<Boolean> callOrderRatings = getOrderApis.setOrderRating(order);
//
//                //Back to home
//                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(homeIntent);
//            }
//        });
    }
}