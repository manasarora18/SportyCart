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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.project.sportycart.entity.AccessTokenDTO;
import com.project.sportycart.entity.RegisterUser;
import com.project.sportycart.retrofit.GetProductsService;
import com.project.sportycart.retrofit.RetrofitClientInstance;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private RegisterUser registerUser = new RegisterUser();
    private Context context;
    private AccessTokenDTO accessTokenDTO;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private int RC_SIGN_IN;

    private String cartValue="";
    String TAG = "logCheck";
    SharedPreferences sp;

    {
        RC_SIGN_IN = 9001;
        context = this;
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

        //LOGIN NOW FROM CART
        Intent loginIntent = getIntent();
        String cartEmpty = loginIntent.getStringExtra("CartPerson");
        if (cartEmpty != null) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), "Login First to Order", Snackbar.LENGTH_SHORT);
            snackbar.show();
            cartValue=loginIntent.getStringExtra("GuestUserId");
            sp=getSharedPreferences("LoginData",MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("User","").apply();
            editor.commit();
        }

        //REGISTER
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
            }

        });

        //CUSTOM LOGIN
        Button loginButton = findViewById(R.id.login);
        sp = getSharedPreferences("LoginData", MODE_PRIVATE);
        String check = sp.getString("LoginCheck", "false");
        if (check.equals("false")) {
            if (!sp.getBoolean("LogInData", false)) {
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
                        else {
                            registerUser.setEmail(user1);
                            registerUser.setPassword(pw);
                            GetProductsService getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
                            Call<AccessTokenDTO> call = getProductsService.loginUser(registerUser);
                            call.enqueue(new Callback<AccessTokenDTO>() {
                                @Override
                                public void onResponse(Call<AccessTokenDTO> call, Response<AccessTokenDTO> response) {
                                    accessTokenDTO = response.body();
                                    sp = getSharedPreferences("LoginData", MODE_PRIVATE);

                                    if (accessTokenDTO.getCheck()) {
                                        System.out.println(accessTokenDTO.getCheck() + "CHECK");
                                        System.out.println("LOGIN DONE");
                                        String userId = accessTokenDTO.getUserId();
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("UserId", userId).apply();
                                        String email = registerUser.getEmail();
                                        editor.putString("Email", email).apply();
                                        editor.putString("LoginCheck", "true").apply();
                                        editor.commit();
                                        Intent loginIntent = new Intent(Login.this, MainActivity.class);
                                        loginIntent.putExtra("GuestUserId",cartValue);
                                        startActivity(loginIntent);
                                        finish();
                                    }
                                    else {
                                        Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), "Invalid Login Details", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                        System.out.println(accessTokenDTO.getCheck() + "FAIL LOGIN CHECK");
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
            } else {
                Intent SignIntent = new Intent(Login.this, MainActivity.class);
                startActivity(SignIntent);
                finish();
            }
        } else {
            Intent LoggedIn = new Intent(Login.this, MainActivity.class);
            startActivity(LoggedIn);
        }

        //GOOGLE LOGIN
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
                if (v.getId() == R.id.sign_in_button) {
                    signIn();
                }
            }
        });

//        //FACEBOOK LOGIN
//        facebookloginButton=findViewById(R.id.facebook_login_button);
//        facebookloginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogIn();
//            }
//        });


        //SKIP LOGIN
        Button skipSignIn = findViewById(R.id.skip);
        skipSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skipSignInIntent = new Intent(Login.this, MainActivity.class);

                int max = Integer.MAX_VALUE;
                int min = 0;
                Random random = new Random();
                int randomNumber = random.nextInt(max - min) + min;
                String guestUserId = String.valueOf(randomNumber);

                System.out.println(guestUserId + "LOGIN GUEST USERID");
                sp = getSharedPreferences("LoginData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("UserId", guestUserId).apply();
                editor.putString("LoginCheck", "false").apply();
                editor.putString("User", "Guest").apply();
                editor.commit();

                startActivity(skipSignInIntent);
            }
        });
    }
    //FACEBOOK LOGIN PART2
//    private void LogIn(){
//        callbackManager=CallbackManager.Factory.create();
//        LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("email"));
//        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                final AccessToken accessToken=loginResult.getAccessToken();
//                final String[] fb_email=new String[1];
//                Bundle bundle=new Bundle();
//                bundle.putString("fields","id,email,name");
//                GraphRequest graphRequest= new GraphRequest(loginResult.getAccessToken(), "me", bundle, null, new GraphRequest.Callback() {
//                    @Override
//                    public void onCompleted(GraphResponse response) {
//                        String email="";
//                        try{
//                            System.out.println("EMAIL FB"+response.getJSONObject().getString("email"));
//                            email=response.getJSONObject().getString("email");
//                            System.out.println("NAME FB"+response.getJSONObject().getString("name"));
//                        }
//                        catch (JSONException e){
//                            e.printStackTrace();
//                        }
//                        String idTokenFB=accessToken.getToken();
//                        System.out.println(idTokenFB);
//                        Bundle parameters = new Bundle();
//                        parameters.putString("fields","first_name,last_name,email,id");
//                        GetProductsService getProductsService=RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
//                        Call<AccessTokenDTO>call=getProductsService.sendFacebookLogin(idTokenFB);
//                        final String finalEmail = email;
//                        call.enqueue(new Callback<AccessTokenDTO>() {
//                            @Override
//                            public void onResponse(Call<AccessTokenDTO> call, Response<AccessTokenDTO> response) {
//                                String userId=response.body().getUserId();
//                                SharedPreferences.Editor editor=sp.edit();
//                                editor.putString("UserId",userId).apply();
//                                editor.putString("Email", finalEmail).apply();
//                                editor.putString("LoginCheck","true").apply();
//                                editor.commit();
//                                System.out.println("FB REGISTERED");
//                                Intent facebookSignInIntent=new Intent(Login.this,MainActivity.class);
//                                startActivity(facebookSignInIntent);
//                                finish();
//                            }
//
//                            @Override
//                            public void onFailure(Call<AccessTokenDTO> call, Throwable t) {
//                                System.out.println("FAILED FB"+t.getMessage());
//                            }
//                        });
//                    }
//                });
//                graphRequest.executeAsync();
//            }
//
//            @Override
//            public void onCancel() {
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//            }
//        });
//
//    }

    //GOOGLE LOGIN PART 2
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            final GoogleSignInAccount account = task.getResult(ApiException.class);
            System.out.println(account.getEmail());
            System.out.println(account.getDisplayName());
            sp = getSharedPreferences("LoginData", MODE_PRIVATE);
            //ID TOKEN SENT AT BACKEND
            String idToken = account.getIdToken();
            System.out.println(idToken + "ID TOKEN");
            GetProductsService getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
            Call<AccessTokenDTO> call = getProductsService.sendGoogleLogin(idToken);
            call.enqueue(new Callback<AccessTokenDTO>() {
                @Override
                public void onResponse(Call<AccessTokenDTO> call, Response<AccessTokenDTO> response) {
                    String userId = response.body().getUserId();
                    System.out.println("USER ID" + userId);
                    final SharedPreferences.Editor editor = sp.edit();
                    editor.putString("User", account.getDisplayName()).apply();
                    editor.putString("Email", account.getEmail()).apply();
                    editor.putString("UserId", userId).apply();
                    editor.putString("LoginCheck", "true").apply();
                    editor.commit();
                    Intent GoogleSignIntent = new Intent(Login.this, MainActivity.class);
                    startActivity(GoogleSignIntent);
                    finish();
                }

                @Override
                public void onFailure(Call<AccessTokenDTO> call, Throwable t) {
                    System.out.println("API CALL FAILED");

                }
            });

        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
}

