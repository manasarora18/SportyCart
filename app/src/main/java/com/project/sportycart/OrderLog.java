package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.project.sportycart.adapter.OrderLogAdapter;
import com.project.sportycart.entity.OrderTable;
import com.project.sportycart.entity.RegisterUser;
import com.project.sportycart.retrofit.GetOrderApis;
import com.project.sportycart.retrofit.GetProductsService;
import com.project.sportycart.retrofit.RetrofitClientInstance;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderLog extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter orderLogAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RegisterUser registerUser;
    List<OrderTable> orderTableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_log);
        layoutManager = new GridLayoutManager(this,1);
        recyclerView = (RecyclerView)findViewById(R.id.recyle_view);
        recyclerView.setLayoutManager(layoutManager);


        GetOrderApis getOrderApis=RetrofitClientInstance.getRetrofitInstance().create(GetOrderApis.class);

        //getOrderApis.getRecentOrders(userId).enqueue(new Callback<List<OrderTable>>() {

        getOrderApis.getRecentOrders("75").enqueue(new Callback<List<OrderTable>>() {
            @Override
            public void onResponse(Call<List<OrderTable>> call, Response<List<OrderTable>> response) {
                orderTableList=response.body();
                for(OrderTable orderTable: orderTableList)
                {
                    OrderCollection.add(orderTable);
                }
            }

            @Override
            public void onFailure(Call<List<OrderTable>> call, Throwable t) {

            }
        });
    }
}

//373683242655-0f1t86uv6ntj57dipepnr9tsdcs7kjll.apps.googleusercontent.com
// deTR-mhHCoSRtb5wqehSQpVu