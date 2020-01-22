package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.project.sportycart.adapter.SearchAdapter;
import com.project.sportycart.entity.Product;
import com.project.sportycart.retrofit.GetProductsService;
import com.project.sportycart.retrofit.RetrofitClientInstance;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResults extends AppCompatActivity implements SearchAdapter.ProductCommunication {
    private RecyclerView searchRecyclerView;
    private RecyclerView.Adapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Intent searchIntent=getIntent();
        String str=searchIntent.getStringExtra("searchKey");

        GetProductsService getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
        Call<List<Product>> call= getProductsService.getSearchData(str);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void generateDataList(List<Product> list){
        searchRecyclerView=findViewById(R.id.search_recycler_view);
        searchAdapter=new SearchAdapter(list,this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        searchRecyclerView.setLayoutManager(gridLayoutManager);
        searchRecyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void onClick(Product product) {
        Intent productIntent=new Intent( SearchResults.this, ProductDetails.class);
        productIntent.putExtra("Image", product.getImageUrl());
        productIntent.putExtra("ProductName",product.getName());
        productIntent.putExtra(("ProductDescription"),(String)product.getDescription());
        if (product.getProductAttributes()!=null) {
            productIntent.putExtra(("ColorAttribute"), (String) product.getProductAttributes().getColor());
            productIntent.putExtra(("SizeAttribute"), (String) product.getProductAttributes().getSize());
            productIntent.putExtra(("MaterialAttribute"), (String) product.getProductAttributes().getMaterial());
            productIntent.putExtra(("PID"),product.getProductId());
        }
        else {
            Toast.makeText(getApplicationContext(),"SEARCHED PRODUCT",Toast.LENGTH_SHORT).show();
        }
        startActivity(productIntent);
    }
}
