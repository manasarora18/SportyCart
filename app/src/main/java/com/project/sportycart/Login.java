package com.project.sportycart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class Login extends AppCompatActivity implements View.OnClickListener {
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN=9001;
    String TAG="logCheck";
    private static final String username="manas@coviam.com";
    private static final String password="1234";
    SharedPreferences sp;

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

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
                        Toast.makeText(getBaseContext(), "Enter Login Details", Toast.LENGTH_SHORT).show();
                    }
                    else if (user1.equals(username) && pw.equals(password)) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("LogInMode", true).apply();
                        editor.putString("User", user1).apply();
                        editor.commit();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Sorry! Wrong user", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
        else {
            Intent SignIntent = new Intent(Login.this, MainActivity.class);
            startActivity(SignIntent);
            finish();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        Button loginGithub=findViewById(R.id.loginGithub);
        loginGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent githubIntent= new Intent(Login.this,LoginGithub.class);
                Toast.makeText(getApplicationContext(),"GitHubLogin",Toast.LENGTH_SHORT).show();
            }
        });

        Button loginFacebook=findViewById(R.id.loginFacebook);
        loginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent facebookIntent=new Intent(Login.this,LoginFacebook.class);
                Toast.makeText(getApplicationContext(),"FacebookLogin",Toast.LENGTH_SHORT).show();
            }
        });

        Button skipSignIn=findViewById(R.id.skip);
        skipSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skipSignInIntent=new Intent(Login.this,MainActivity.class);
                startActivity(skipSignInIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        signIn();

    }

    private void signIn() {
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount>task){
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            System.out.println(account.getEmail());
            System.out.println(account.getDisplayName());
            sp=getSharedPreferences("LoginData",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("LogInMode", true).apply();
            editor.putString("User", account.getDisplayName()).apply();
            editor.putString("Email",account.getEmail()).apply();
            editor.commit();
            Intent GoogleSignIntent =new Intent(Login.this, MainActivity.class);
            GoogleSignIntent.putExtra("Email",account.getEmail());
            GoogleSignIntent.putExtra("User",account.getDisplayName());

            startActivity(GoogleSignIntent);
            finish();

        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
//GOOGLE API CLIENT LOGIN KEY
//797175891043-3je1gnb30pq9m8eum6us67uh2c3t7eie.apps.googleusercontent.com
//AB:1E:23:61:7D:7A:4A:9E:A7:DB:88:4A:4B:B9:9A:2B:CD:05:23:F3
//yrx5bAa79Cyvjs2AQk1ct70s

//GOOGLE API LOGIN KEY
//797175891043-tt721gbd5fvm8i8ckg3kobr38jn4a31b.apps.googleusercontent.com
//FKtPQzX-PybNcCOA65t8tl5v