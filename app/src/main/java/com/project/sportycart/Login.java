package com.project.sportycart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private static final String username="MANAS@coviam.com";
    private static final String password="1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button=findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user=findViewById(R.id.username);
                EditText pass=findViewById(R.id.password);
                final String user1= String.valueOf(user.getText());
                final String pw= String.valueOf(pass.getText());

                System.out.println("Saved!");
                SharedPreferences sharedPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("First Name",user.getText().toString());
                editor.commit();

                if(user1.equals(username) && pw.equals(password)) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Sorry! Wrong user",Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
