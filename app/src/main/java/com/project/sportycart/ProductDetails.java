package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ProductDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent productIntent=getIntent();

        String imageUrl=productIntent.getStringExtra("Image");
        ImageView imageView=findViewById(R.id.productimage);
        Glide.with(getBaseContext()).load(imageUrl).into(imageView);

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

//        String bestPrice=intent.getStringExtra("BestPrice");
//        TextView bestPriceText=findViewById(R.id.bestPrice);
//        bestPriceText.setText(bestPrice);

//        String merchantDetails=intent.getStringExtra("MerchantDetails");
//        TextView merchant=findViewById(R.id.merchantDetails);
//        merchant.setText(merchantDetails);


    }
}
