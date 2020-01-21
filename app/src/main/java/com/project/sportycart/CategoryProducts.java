package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.project.sportycart.adapter.CategoryAdapter;
import com.project.sportycart.entity.Product;
import com.project.sportycart.retrofit.GetProductsService;
import com.project.sportycart.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProducts extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private RecyclerView.Adapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        GetProductsService getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
        Intent intent=getIntent();
        Integer categoryId=intent.getIntExtra("CategoryId",1);
//        Integer i = Integer.parseInt(categoryId);
        Call<List<Product>> call= getProductsService.getCategoryProducts(categoryId);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(CategoryProducts.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void generateDataList(List<Product> body){
        categoryRecyclerView=findViewById(R.id.my_recycler_view);
        categoryAdapter=new CategoryAdapter(this,body);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }
}
