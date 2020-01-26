package com.project.sportycart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.project.sportycart.adapter.CartAdapter;
import com.project.sportycart.entity.Cart;
import com.project.sportycart.retrofit.GetCartApis;
import com.project.sportycart.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCartActivity extends AppCompatActivity implements CartAdapter.ICartCommunicator {
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager cartLayoutManager;
    //rucha
    GetCartApis getCartApis;
    List<Cart> dataItemList;
    public boolean flag = true;
    public boolean cartFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        recyclerView = findViewById(R.id.recycler_view);
        cartLayoutManager = new LinearLayoutManager(this);
        //all cart related apis
        //currently user id ->75
        getCartItems();
        //confirm order --> showing total amount of all cart items of a user
        processConfirmOrder();
    }

    //update quantity of cart for the user id(75 currently)
    @Override
    public boolean updateQuantity(String productId, int quantity,String merchantId) {
        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", "");
        Call<Boolean> callUpdateQuantity = getCartApis.updateCartQuantity(productId, userId, quantity,merchantId);
//        Call<Boolean> callUpdateQuantity = getCartApis.updateCartQuantity(productId, "75", quantity,merchantId);
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

    @Override
    public boolean removeFromCart(String merchantId, String productId) {
        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", "");
        Call<Boolean> callRemoveFromCart = getCartApis.deleteCartItem(userId, merchantId, productId);
//        Call<Boolean> callRemoveFromCart = getCartApis.deleteCartItem("75", merchantId, productId);
        callRemoveFromCart.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("Inside OnResponse RemoveFromCart");
                cartFlag = true;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("Inside OnFailure RemoveFromCart");
                cartFlag = false;
            }
        });
        return cartFlag;
    }

    void getCartItems() {
        getCartApis = RetrofitClientInstance.getRetrofitInstance().create(GetCartApis.class);
        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", "");
        //Get Cart Details of a user
        Call<List<Cart>> call = getCartApis.getAllCartItems(userId);
//        Call<List<Cart>> call = getCartApis.getAllCartItems("75");
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
                if (dataItemList != null) {
                    for (Cart data : dataItemList) {
                        CartCollection.add(data);
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Cart is Empty!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                System.out.println("Inside OnFailure");
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    void processConfirmOrder() {
        Button confirmOrder = findViewById(R.id.confirmOrderButton);
        confirmOrder.setEnabled(true);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
                String userId = sharedPreferences.getString("UserId", "");
                /*if (userId.equals("")) {
                    Intent loginFirst = new Intent(ViewCartActivity.this, Login.class);
                    Toast.makeText(getApplicationContext(), "Login First to Order", Toast.LENGTH_SHORT);
                    loginFirst.putExtra("CartPerson", "LoginFirst");
                    startActivity(loginFirst);
                } else {*/
                    Intent orderIntent = new Intent(getApplicationContext(), OrderActivity.class);
                    double sum = 0;
                    for (Cart cart : dataItemList) {
                        if (cart.getQuantity() == 0) {
                            continue;
                        }
                        sum = sum + (cart.getPrice()) * cart.getQuantity();
                        System.out.println(String.valueOf(sum));
                    }
                    if (sum > 0) {
                        orderIntent.putExtra("totalAmount", String.valueOf(sum));
                        startActivity(orderIntent);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Cart Empty...", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            //}
        });
    }
}