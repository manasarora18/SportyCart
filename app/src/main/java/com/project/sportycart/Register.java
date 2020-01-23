package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.snackbar.Snackbar;
import com.project.sportycart.entity.RegisterUser;
import com.project.sportycart.retrofit.GetProductsService;
import com.project.sportycart.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private RegisterUser registerUser=new RegisterUser();
    private EditText registerUsername;
    private EditText registerEmail;
    private EditText registerPassword;
    private EditText registerConfirmPassword;
    private EditText registerPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button signUp=findViewById(R.id.register);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUsername=findViewById(R.id.registerusername);
                String userName=registerUsername.getText().toString();

                registerEmail=findViewById(R.id.registeremail);
                String email=registerEmail.getText().toString();

                registerPassword=findViewById(R.id.registerpassword);
                String password=registerPassword.getText().toString();

                registerConfirmPassword=findViewById(R.id.registerconfirmpassword);
                String confirmPassword=registerConfirmPassword.getText().toString();

                registerPhone=findViewById(R.id.registerphone);
                long phone=Long.parseLong(String.valueOf(registerPhone.getText()));

//                System.out.println(userName+email+password+confirmPassword+phone);

                Boolean nullFlag=true;
                Boolean passwordCheckFail=true;
                String message;

                if(userName!= null && email!=null && password!=null && confirmPassword!=null && phone!=0){
                    nullFlag=false;
                    if(confirmPassword.equals(password)){
                        passwordCheckFail=false;
                        registerUser.setEmail(email);
                        registerUser.setPassword(password);
                        registerUser.setPhoneNo(phone);
                        registerUser.setUserName(userName);
                        message="Registered";
                    }
                    else{
                        message="Confirm Password Failed";
                    }
                }
                else{
                    message="Enter all fields appropriately!";
                }

                if(nullFlag==false && passwordCheckFail==false){
                    GetProductsService getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
                    Call<String> call= getProductsService.addUser(registerUser);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            registerUser.setUserId(response);
                            System.out.println("REGISTERED");
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            System.out.println("Invalid Backend Response"+t.getMessage());

                        }
                    });
                }

                Snackbar snackbar = Snackbar.make(findViewById(R.id.register_layout),
                        message, Snackbar.LENGTH_SHORT);
                snackbar.show();
                Intent loginNow= new Intent(Register.this,Login.class);
                startActivity(loginNow);
                finish();
            }
        });
    }
}
