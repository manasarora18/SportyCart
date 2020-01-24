package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.project.sportycart.adapter.ProductAdapter;
import com.project.sportycart.entity.Cart;
import com.project.sportycart.entity.MerchantDetails;
import com.project.sportycart.retrofit.GetProductsService;
import com.project.sportycart.retrofit.RetrofitClientInstance;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetails extends AppCompatActivity implements ProductAdapter.MerchantDetailsInterface {
    private RecyclerView mercRecyclerView;
    private SharedPreferences sharedPreferences;
    private MerchantDetails merchantDetails;
    private ProductAdapter productAdapter;
    private Cart cart=new Cart();
    String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        final Intent productIntent = getIntent();

        String imageUrl = productIntent.getStringExtra("Image");
        ImageView imageView = findViewById(R.id.productimage);
//        Glide.with(getBaseContext()).load(imageUrl).into(imageView);
        Picasso.with(getBaseContext()).load(imageUrl).into(imageView);

        String productName = productIntent.getStringExtra("ProductName");
        TextView title = findViewById(R.id.title);
        title.setText(productName);

        String productDescription = productIntent.getStringExtra("ProductDescription");
        TextView description = findViewById(R.id.description);
        description.setText(productDescription);

        String color = productIntent.getStringExtra("ColorAttribute");
        TextView colorText = findViewById(R.id.colorAttribute);
        colorText.setText(color);

        String size = productIntent.getStringExtra("SizeAttribute");
        TextView sizeText = findViewById(R.id.sizeAttribute);
        sizeText.setText(size);

        String material = productIntent.getStringExtra("MaterialAttribute");
        TextView materialText = findViewById(R.id.materialAttribute);
        materialText.setText(material);
        pid = productIntent.getStringExtra("PID");
//        System.out.println("PID"+pid);


        GetProductsService getProductsService=RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
        Call<List<MerchantDetails>> call=getProductsService.getMerchantDetails(pid);
        call.enqueue(new Callback<List<MerchantDetails>>() {
            @Override
            public void onResponse(Call<List<MerchantDetails>> call, Response<List<MerchantDetails>> response) {
                mercRecyclerView=findViewById(R.id.merc_recycler_view);
                List<MerchantDetails>merchantDetailsList=response.body();
                productAdapter=new ProductAdapter(merchantDetailsList,ProductDetails.this);
                GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(), 1);
                mercRecyclerView.setLayoutManager(gridLayoutManager);
                mercRecyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<List<MerchantDetails>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void onClick(MerchantDetails merchantDetails) {
        cart.setProductId(pid);
        cart.setQuantity(1);
        sharedPreferences=getSharedPreferences("LoginData",MODE_PRIVATE);
        String userId=sharedPreferences.getString("UserId","");
//        cart.setUserId(userId);

        cart.setUserId("75");
        cart.setPrice(merchantDetails.getPrice());
        cart.setMerchantId(merchantDetails.getMerchantId());
        System.out.println(cart);
        GetProductsService getProductsService= RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);

        Call<String> call=getProductsService.addToCart(cart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.product_details_layout),
                        "Added to Cart", Snackbar.LENGTH_SHORT);
                snackbar.show();
                System.out.println("MADE A CALL");

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ProductDetails.this,t.getMessage(),Toast.LENGTH_SHORT);
                System.out.println("FAILED A CALL");

            }
        });

    }
}
