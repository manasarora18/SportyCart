package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;


public class ProductDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        final Intent productIntent=getIntent();

        String imageUrl=productIntent.getStringExtra("Image");
        ImageView imageView=findViewById(R.id.productimage);
        Glide.with(getBaseContext()).load(imageUrl).into(imageView);
        Picasso.with(getBaseContext()).load(imageUrl).into(imageView);

        String productName=productIntent.getStringExtra("ProductName");
        TextView title=findViewById(R.id.title);
        title.setText(productName);

        String productDescription=productIntent.getStringExtra("ProductDescription");
        TextView description=findViewById(R.id.description);
        description.setText(productDescription);

        String color=productIntent.getStringExtra("ColorAttribute");
        TextView colorText=findViewById(R.id.colorAttribute);
        colorText.setText(color);

        String size=productIntent.getStringExtra("SizeAttribute");
        TextView sizeText=findViewById(R.id.sizeAttribute);
        sizeText.setText(size);

        String material=productIntent.getStringExtra("MaterialAttribute");
        TextView materialText=findViewById(R.id.materialAttribute);
        materialText.setText(material);
        final String pid=productIntent.getStringExtra("PID");
//
//        Button addToCart=findViewById(R.id.addToCart);
//        addToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                GetProductsService getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
//                Call<MerchantDetails> call= getProductsService.sendToCart(pid);
//                call.enqueue(new Callback<List<Product>>() {
//                    @Override
//                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                        mercRecyclerView=findViewById(R.id.merc_recycler_view);
//                        Product product;
//                        homeAdapter=new HomeAdapter(list,MainActivity.this);
//                        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
//                        recyclerView.setLayoutManager(gridLayoutManager);
//                        recyclerView.setAdapter(homeAdapter);
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Product>> call, Throwable t) {
//
//                    }
//                });
//            }
//        });
//

//        String bestPrice=intent.getStringExtra("BestPrice");
//        TextView bestPriceText=findViewById(R.id.bestPrice);
//        bestPriceText.setText(bestPrice);

//        String merchantDetails=intent.getStringExtra("MerchantDetails");
//        TextView merchant=findViewById(R.id.merchantDetails);
//        merchant.setText(merchantDetails);
    }
}
