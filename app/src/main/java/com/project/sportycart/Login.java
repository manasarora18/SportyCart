package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    private static final String username="MANAS@coviam.com";
    private static final String password="1234";
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent registerIntent=new Intent(Login.this,Register.class);
            startActivity(registerIntent);
            }

        });

        Button loginButton= findViewById(R.id.login);
        sp=getSharedPreferences("LoginData",MODE_PRIVATE);
        if(!sp.getBoolean("LogInMode",false)) {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText user = findViewById(R.id.username);
                    EditText pass = findViewById(R.id.password);
                    final String user1 = String.valueOf(user.getText());
                    final String pw = String.valueOf(pass.getText());

                    if (user1.length() == 0 || pw.length() == 0) {
                        Toast.makeText(getBaseContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                    } else if (user1.equals(username) && pw.equals(password)) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("LogInMode", true).apply();
                        editor.putString("User", user1).apply();
                        editor.commit();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry! Wrong user", Toast.LENGTH_LONG).show();
                    }
                }

            });
        }

        else {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        Button loginGithub=findViewById(R.id.loginGithub);
        loginGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent githubIntent= new Intent(Login.this,LoginGithub.class);
                Toast.makeText(getApplicationContext(),"GitHubLogin",Toast.LENGTH_LONG).show();
            }
        });

        Button loginFacebook=findViewById(R.id.loginFacebook);
        loginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent facebookIntent=new Intent(Login.this,LoginFacebook.class);
                Toast.makeText(getApplicationContext(),"FacebookLogin",Toast.LENGTH_LONG).show();
            }
        });
    }
}
