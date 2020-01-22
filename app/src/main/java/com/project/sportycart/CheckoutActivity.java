package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.sportycart.entity.Order;
import com.project.sportycart.retrofit.GetOrderApis;
import com.project.sportycart.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        TextView orderPlacedText=findViewById(R.id.orderPlacedText);
        orderPlacedText.setText("Order Your has been placed successfully and will be delivered to you shortly," +
                " Thank you for choosing SportyCart!!!");

        GetOrderApis getOrderApis;
        getOrderApis= RetrofitClientInstance.getRetrofitInstance().create(GetOrderApis.class);
        Call<List<Order>> callCheckoutOrder=getOrderApis.checkoutOrder("75");
        callCheckoutOrder.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                System.out.println("Inside Checkout onResponse");
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                System.out.println("Inside Checkout onFailure");

            }
        });


        ImageButton backToHome=(ImageButton)findViewById(R.id.imageButton);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}
