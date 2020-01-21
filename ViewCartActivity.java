package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager cartLayoutManager;
    ApiInterface apiInterface;
    List<Integer> myList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        recyclerView = findViewById(R.id.recycler_view);
        cartLayoutManager = new LinearLayoutManager(this);

        apiInterface = AppController.getRetrofit().create(ApiInterface.class);
        Call<List<Product>> call = apiInterface.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> dataItemList = response.body();
                recyclerView.setLayoutManager(cartLayoutManager);
                recyclerView.setHasFixedSize(true);

                adapter = new CartAdapter(dataItemList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast toast=Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        for (int i = 0; i < 10; i++) {
            myList.add(i);
        }
        //adapter = new CartAdapter(myList);
        //recyclerView.setAdapter(adapter);

    }
}
