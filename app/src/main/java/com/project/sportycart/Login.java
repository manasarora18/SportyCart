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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.project.sportycart.categoryActivity.ContentItem;
import com.project.sportycart.entity.AccessTokenDTO;
import com.project.sportycart.entity.RegisterUser;
import com.project.sportycart.retrofit.GetProductsService;
import com.project.sportycart.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity{
    private RegisterUser registerUser=new RegisterUser();
    private Context context;
    private AccessTokenDTO accessTokenDTO;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private int RC_SIGN_IN;
    String TAG="logCheck";
//    private static final String username="manas@coviam.com";
//    private static final String password="1234";
    SharedPreferences sp;

    {RC_SIGN_IN = 9001;
    context=this;
    }
    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent loginIntent=getIntent();
        String cartLogin=loginIntent.getStringExtra("CartPerson");
        if(cartLogin!=null){
            Snackbar snackbar=Snackbar.make(findViewById(R.id.login_layout),"Login First to Order",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

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
//                    else if (user1.equals(username) && pw.equals(password)) {
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putBoolean("LogInMode", true).apply();
//                        editor.putString("User", user1).apply();
//                        editor.commit();
//                        Intent intent = new Intent(Login.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
                    else {
                        registerUser.setEmail(user1);
                        registerUser.setPassword(pw);
                        GetProductsService getProductsService= RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
                        Call<AccessTokenDTO> call=getProductsService.loginUser(registerUser);
                        call.enqueue(new Callback<AccessTokenDTO>() {
                            @Override
                            public void onResponse(Call<AccessTokenDTO> call, Response<AccessTokenDTO> response) {
                                accessTokenDTO=response.body();
                                sp=getSharedPreferences("LoginData",MODE_PRIVATE);

                                if(accessTokenDTO.getCheck()) {
                                    System.out.println(accessTokenDTO.getCheck()+"CHECK");
                                    System.out.println("LOGIN DONE");
                                    String userId=accessTokenDTO.getUserId();
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("UserId",userId).apply();
                                    String email=registerUser.getEmail();
                                    editor.putString("Email",email).apply();
                                    editor.commit();
                                    Intent loginIntent = new Intent(Login.this, MainActivity.class);
                                    startActivity(loginIntent);
                                }
                                else{
                                    Snackbar snackbar=Snackbar.make(findViewById(R.id.login_layout),"Invalid Login Details",Snackbar.LENGTH_LONG);
                                    System.out.println(accessTokenDTO.getCheck()+"FAILCHECK");
                                }

                            }

                            @Override
                            public void onFailure(Call<AccessTokenDTO> call, Throwable t) {
                                Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout),
                                        t.getMessage(), Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        });

//                        Toast.makeText(getApplicationContext(), "Sorry! Wrong user", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
        else {
            Intent SignIntent = new Intent(Login.this, MainActivity.class);
            startActivity(SignIntent);
            finish();
        }

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("959388190902-n2h383o5ej00boqakorh0qn4iodcnd95.apps.googleusercontent.com")
                .requestEmail()
                .requestProfile()
                .build();
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.sign_in_button){
                    signIn();
                }
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


    private void signIn() {
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            final SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("LogInMode", true).apply();
            editor.putString("User", account.getDisplayName()).apply();
            editor.putString("Email",account.getEmail()).apply();
            //ID TOKEN SENT AT BACKEND
            String idToken=account.getIdToken();
            System.out.println(idToken+"ID TOKEN");
            editor.commit();
            Intent GoogleSignIntent =new Intent(Login.this, MainActivity.class);
            GoogleSignIntent.putExtra("Email",account.getEmail());
            GoogleSignIntent.putExtra("User",account.getDisplayName());
            GetProductsService getProductsService=RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
            Call<AccessTokenDTO> call=getProductsService.sendGoogleLogin(idToken);
            call.enqueue(new Callback<AccessTokenDTO>() {
                @Override
                public void onResponse(Call<AccessTokenDTO> call, Response<AccessTokenDTO> response) {
                    String userId=response.body().getUserId();
                    System.out.println("USER ID"+userId);
                    editor.putString("UserId",userId).apply();
                    editor.commit();
                }

                @Override
                public void onFailure(Call<AccessTokenDTO> call, Throwable t) {
                    System.out.println("API CALL FAILED");

                }
            });

            startActivity(GoogleSignIntent);
            finish();

        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
}

