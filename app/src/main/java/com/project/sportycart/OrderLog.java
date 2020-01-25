package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.project.sportycart.adapter.OrderLogAdapter;
import com.project.sportycart.entity.OrderTable;
import com.project.sportycart.entity.RegisterUser;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_log);
        layoutManager = new GridLayoutManager(this,1);
        recyclerView = (RecyclerView)findViewById(R.id.recyle_view);
        recyclerView.setLayoutManager(layoutManager);
        GetProductsService getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
        getProductsService.getOrderLog(registerUser).enqueue(new Callback<List<OrderTable>>() {
            @Override
            public void onResponse(Call<List<OrderTable>> call, Response<List<OrderTable>> response) {
                List<OrderTable> list = response.body();
                orderLogAdapter  = new OrderLogAdapter(list);
                recyclerView.setAdapter(orderLogAdapter);
            }

            @Override
            public void onFailure(Call<List<OrderTable>> call, Throwable t) {

            }
        });
    }
}