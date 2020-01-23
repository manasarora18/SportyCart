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

public class CategoryProducts extends AppCompatActivity implements CategoryAdapter.ProductCommunication{
    private RecyclerView categoryRecyclerView;
    private RecyclerView.Adapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);
        System.out.println("IN CATEGORY");
        Intent intent=getIntent();
        Integer categoryId=intent.getIntExtra("categoryId",3);
        System.out.println("InCategory:"+categoryId);

        GetProductsService getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
        Call<List<Product>> call= getProductsService.getCategoryProducts(1,categoryId);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                generateDataList(response.body());
                System.out.println("API HIT");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateDataList(List<Product> list){
        categoryRecyclerView=findViewById(R.id.cat_recycler_view);
        categoryAdapter=new CategoryAdapter(list,this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onClick(Product product) {
        Intent productIntent=new Intent( CategoryProducts.this, ProductDetails.class);
        productIntent.putExtra("Image", product.getImageUrl());
        productIntent.putExtra("ProductName",product.getName());
        productIntent.putExtra(("ProductDescription"),(String)product.getDescription());
        if(product.getProductAttributes()!=null) {
            productIntent.putExtra(("ColorAttribute"), (String) product.getProductAttributes().getColor());
            productIntent.putExtra(("SizeAttribute"), (String) product.getProductAttributes().getSize());
            productIntent.putExtra(("MaterialAttribute"), (String) product.getProductAttributes().getMaterial());
            productIntent.putExtra(("PID"),product.getProductId());
        }
        else{
            Toast.makeText(getApplicationContext(),"CATEGORY PRODUCT",Toast.LENGTH_SHORT).show();
        }
        startActivity(productIntent);
    }
}
