package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.project.sportycart.entity.UserDTO;
import com.project.sportycart.retrofit.GetProductsService;
import com.project.sportycart.retrofit.RetrofitClientInstance;

import java.nio.file.Path;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetails extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    GetProductsService getProductsService;
    private SharedPreferences sharedPreferences;
    UserDTO userDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        getUserDetails();
    }

    void getUserDetails() {
        getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        final String userId = sharedPreferences.getString("UserId", "");

        Call<UserDTO> callUserDetails = getProductsService.getUserProfile(userId);
        callUserDetails.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {

                userDTO = response.body();
                //adapter=new
                if (userDTO != null) {
                    if (userDTO.getUserName() != null) {
                        TextView userName = findViewById(R.id.userName);
                        userName.setText(userDTO.getUserName());


                    }
                    if (userDTO.getEmail() != null) {

                        TextView userEmail = findViewById(R.id.userEmail);
                        userEmail.setText(userDTO.getEmail());

                    }
                    TextView userAddress = findViewById(R.id.userAddress);


                    System.out.println("Phone: " + userDTO.getPhoneNo());
//
                    if (String.valueOf(userDTO.getPhoneNo()) !=null) {
                        TextView userPhone = findViewById(R.id.userPhone);
                        userPhone.setText(String.valueOf(userDTO.getPhoneNo()));
                    }
                    else
                    {
                        TextView userPhoneText = findViewById(R.id.userPhoneText);
                        userPhoneText.setVisibility(View.INVISIBLE);

                    }

                    if (userDTO.getAddress() == null) {
                        TextView userAddressText = findViewById(R.id.userAddressText);
                        userAddressText.setVisibility(View.INVISIBLE);
                    } else {
                        userAddress.setText(userDTO.getAddress());
                    }
                }
                System.out.println("OnResponse User Details");
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                System.out.println("OnFailure User Details");
            }
        });

    }
}
