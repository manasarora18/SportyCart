package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        //recyclerView.setLayoutManager(cartLayoutManager);

        apiInterface = AppController.getRetrofit().create(ApiInterface.class);
        Call<List<RawData>> call = apiInterface.getData();
        call.enqueue(new Callback<List<RawData>>() {
            @Override
            public void onResponse(Call<List<RawData>> call, Response<List<RawData>> response) {
                List<RawData> dataItemList = response.body();
                recyclerView.setLayoutManager(cartLayoutManager);
                //recyclerView.setAdapter(adapter);
                recyclerView.setAdapter(new CartAdapter(dataItemList));

            }

            @Override
            public void onFailure(Call<List<RawData>> call, Throwable t) {

            }
        });
        for (int i = 0; i < 10; i++) {
            myList.add(i);
        }
        //adapter = new CartAdapter(myList);
        //recyclerView.setAdapter(adapter);

    }
}
