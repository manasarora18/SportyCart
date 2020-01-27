package com.project.sportycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.project.sportycart.adapter.CategoryAdapter;
import com.project.sportycart.categoryActivity.CategoryPageResponse;
import com.project.sportycart.categoryActivity.ContentItem;
import com.project.sportycart.entity.Product;
import com.project.sportycart.retrofit.GetProductsService;
import com.project.sportycart.retrofit.RetrofitClientInstance;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProducts extends AppCompatActivity implements CategoryAdapter.ProductCommunication {
    private RecyclerView categoryRecyclerView;
    private RecyclerView.Adapter categoryAdapter;
    private Context context;
    private int size = 2;
    private int page = 0;
    private Integer categoryId;
    private List<ContentItem> list;
    GridLayoutManager gridLayoutManager;
    private int pos;
    private int totalPages;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        list = new ArrayList<>();
        Intent intent=getIntent();
        categoryId=intent.getIntExtra("categoryId",3);
        System.out.println("InCategory:"+categoryId);
        categoryRecyclerView=findViewById(R.id.cat_recycler_view);
        gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryAdapter(CategoryProducts.this, list, CategoryProducts.this);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                progressBar.setVisibility(View.VISIBLE);
                pos = gridLayoutManager.findLastCompletelyVisibleItemPosition();

                if((pos==list.size()-1)&&(page!=totalPages)){
                    page++;
                    apiCall(page,size);
                }
            }
        });

        apiCall(page,size);

    }

    private void apiCall(int page,int size){
        GetProductsService getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
        Call<CategoryPageResponse> call= getProductsService.getCategoryProducts(categoryId,page,size);
        call.enqueue(new Callback<CategoryPageResponse>() {
            @Override
            public void onResponse(Call<CategoryPageResponse> call, Response<CategoryPageResponse> response) {
                int length = list.size();
                list.addAll(response.body().getContent());
                totalPages =  response.body().getTotalPages();
                progressBar.setVisibility(View.INVISIBLE);
                categoryAdapter.notifyItemRangeInserted(length,response.body().getContent().size());
                System.out.println("OnResponse CategoryProducts");
            }

            @Override
            public void onFailure(Call<CategoryPageResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println("OnFailure CategoryProducts"+t.getMessage());
            }
        });
    }

    @Override
    public void onClick(ContentItem product) {
        Intent productIntent=new Intent( CategoryProducts.this, ProductDetails.class);
        productIntent.putExtra("Image", product.getImageUrl());
        productIntent.putExtra("ProductName",product.getName());
        productIntent.putExtra(("ProductDescription"),(String)product.getDescription());
        productIntent.putExtra(("PID"),product.getProductId());
        if(product.getProductAttributes()!=null) {
            productIntent.putExtra(("ColorAttribute"), (String) product.getProductAttributes().getColor());
            productIntent.putExtra(("SizeAttribute"), (String) product.getProductAttributes().getSize());
            productIntent.putExtra(("MaterialAttribute"), (String) product.getProductAttributes().getMaterial());
        }
        else{
            Toast.makeText(getApplicationContext(),"CATEGORY PRODUCT",Toast.LENGTH_SHORT).show();
        }
        startActivity(productIntent);
    }
}
