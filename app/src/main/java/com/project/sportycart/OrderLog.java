package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.project.sportycart.adapter.OrderLogAdapter;
import com.project.sportycart.entity.Order;
import com.project.sportycart.entity.OrderTable;
import com.project.sportycart.entity.RegisterUser;
import com.project.sportycart.retrofit.GetOrderApis;
import com.project.sportycart.retrofit.GetProductsService;
import com.project.sportycart.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderLog extends AppCompatActivity implements OrderLogAdapter.IOrderCommunicator {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter orderLogAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RegisterUser registerUser;
    List<OrderTable> orderTableList;
    GetOrderApis getOrderApis;
    private SharedPreferences sharedPreferences;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_log);
        layoutManager = new GridLayoutManager(this, 1);
        recyclerView = (RecyclerView) findViewById(R.id.recyle_view);
        recyclerView.setLayoutManager(layoutManager);

        getOrderApis = RetrofitClientInstance.getRetrofitInstance().create(GetOrderApis.class);
        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", "");


        Call<List<OrderTable>> callOrderList = getOrderApis.getRecentOrders(userId);//userId
        callOrderList.enqueue(new Callback<List<OrderTable>>() {
            @Override
            public void onResponse(Call<List<OrderTable>> call, Response<List<OrderTable>> response) {
                orderTableList = response.body();
                System.out.println(orderTableList);
                orderLogAdapter = new OrderLogAdapter(orderTableList, OrderLog.this);
                recyclerView.setAdapter(orderLogAdapter);

                if (orderTableList != null) {
                    for (OrderTable orderTable : orderTableList) {
                        OrderCollection.add(orderTable);
                    }

                }

                System.out.println("Order Log OnResponse");
            }

            @Override
            public void onFailure(Call<List<OrderTable>> call, Throwable t) {

                System.out.println("Order Log OnFailure");

            }
        });

    }

    @Override
    public boolean setRating(String orderId, String productId, String merchantId, String userId, double rating) {
        Call<Boolean> callOrderRating = getOrderApis.setOrderRating(orderId, productId, merchantId, userId, rating);
        callOrderRating.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("Set Order Rating onResponse");
                flag = true;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("Set Order Rating onFailure");
                flag = false;

            }
        });
        return flag;
    }

}

