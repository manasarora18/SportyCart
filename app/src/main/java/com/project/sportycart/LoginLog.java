package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.project.sportycart.adapter.LoginLogAdapter;
import com.project.sportycart.entity.RegisterUser;
import com.project.sportycart.entity.UserLog;
import com.project.sportycart.retrofit.GetProductsService;
import com.project.sportycart.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginLog extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView loginRecyclerView;
    private RegisterUser registerUser=new RegisterUser();
    private SharedPreferences sharedPreferences;
    private LoginLogAdapter loginLogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_log);
        sharedPreferences=getSharedPreferences("LoginData",MODE_PRIVATE);
        String userId=sharedPreferences.getString("UserId","");
        registerUser.setUserId(userId);

        layoutManager = new GridLayoutManager(this,1);
        loginRecyclerView = (RecyclerView)findViewById(R.id.login_recycle_view);
        loginRecyclerView.setLayoutManager(layoutManager);
        GetProductsService getProductsService= RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
        getProductsService.getLoginLog(userId).enqueue(new Callback<List<UserLog>>() {
            @Override
            public void onResponse(Call<List<UserLog>> call, Response<List<UserLog>> response) {
                List<UserLog> list = response.body();
                loginLogAdapter = new LoginLogAdapter(list);
                loginRecyclerView.setAdapter(loginLogAdapter);
            }

            @Override
            public void onFailure(Call<List<UserLog>> call, Throwable t) {

            }
        });
    }
}
