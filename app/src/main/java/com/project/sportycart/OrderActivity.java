package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.sportycart.entity.Order;
import com.project.sportycart.retrofit.GetOrderApis;
import com.project.sportycart.retrofit.RetrofitClientInstance;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

       /* GetOrderApis getOrderApis;
        getOrderApis = RetrofitClientInstance.getRetrofitInstance().create(GetOrderApis.class);
        Call<List<Order>> callSaveOrder = getOrderApis.saveOrder("75");
        callSaveOrder.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orderList = response.body();

            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                System.out.println("Order OnFailure");
            }
        });*/

        Intent intent = getIntent();
        String str = intent.getStringExtra("totalAmount");
        TextView orderTotalAmount = findViewById(R.id.orderTotalAmount);
        orderTotalAmount.setText(str);


        Button checkoutButton = findViewById(R.id.checkoutButton);
        TextView quantityText = findViewById(R.id.quantityText);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkOutIntent = new Intent(getApplicationContext(), CheckoutActivity.class);
                //Bundle bundle = new Bundle();
                //bundle.putParcelable("orderList", orderList);
                //checkOutIntent.putExtras(bundle);
                //for(Order o:orderList) {
                //    checkOutIntent.putExtra("orderList", orderList.get(0));
                //System.out.println(orderList.get(0));
                //}
                startActivity(checkOutIntent);

            }
        });

    }
}
