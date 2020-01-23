package com.project.sportycart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.sportycart.adapter.CartAdapter;
import com.project.sportycart.entity.Cart;
import com.project.sportycart.retrofit.GetCartApis;
import com.project.sportycart.retrofit.RetrofitClientInstance;

import java.io.Serializable;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCartActivity extends AppCompatActivity implements CartAdapter.ICartCommunicator {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager cartLayoutManager;

    GetCartApis getCartApis;
    List<Cart> dataItemList;
    public boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        recyclerView = findViewById(R.id.recycler_view);
        cartLayoutManager = new LinearLayoutManager(this);


        //all cart related apis
        //currently user id ->75
        getCartApis = RetrofitClientInstance.getRetrofitInstance().create(GetCartApis.class);

        //Get Cart Details of a user
        Call<List<Cart>> call = getCartApis.getAllCartItems("75");
        call.enqueue(new Callback<List<Cart>>() {

            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                dataItemList = response.body();
                System.out.println(dataItemList);
                recyclerView.setLayoutManager(cartLayoutManager);
                recyclerView.setHasFixedSize(true);

                adapter = new CartAdapter(dataItemList, ViewCartActivity.this);
                recyclerView.setAdapter(adapter);
                System.out.println("Inside OnResponse");
                //CartCollection cartCollection;
                for(Cart data:dataItemList)
                {
                    CartCollection.add(data);
                }


            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                System.out.println("Inside OnFailure");
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        //confirm order --> showing total amount of all cart items of a user

        processConfirmOrder();

    }


    //update quantity of cart for the user id(75 currently)
    @Override
    public boolean updateQuantity(String productId, int quantity) {
        Call<Boolean> callUpdateQuantity = getCartApis.updateCartQuantity(productId, "75", quantity);
        callUpdateQuantity.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("Inside updateQuantity OnResponse");
                flag = true;


            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("Inside updateQuantity OnFailure");
                flag = false;
            }
        });
        return flag;

    }

    void processConfirmOrder()
    {
        Button confirmOrder = findViewById(R.id.confirmOrderButton);
        TextView quantityText = (TextView) findViewById(R.id.quantityText);
       /* if (quantityText.getText().toString().trim().equals("0")) {
            confirmOrder.setEnabled(false);
        } else {*/

        confirmOrder.setEnabled(true);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent(getApplicationContext(), OrderActivity.class);

                double sum = 0;
                for (Cart c : dataItemList) {
                    sum = sum + (c.getPrice()) * c.getQuantity();
                    System.out.println(String.valueOf(sum));
                }
                orderIntent.putExtra("totalAmount", String.valueOf(sum));

                startActivity(orderIntent);

                //Intent cartIntent=new Intent()
                //finish();

            }
        });
        // }

    }
}
