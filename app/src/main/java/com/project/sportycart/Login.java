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
    private LoginButton facebookloginButton;
    private CallbackManager callbackManager;

    String TAG="logCheck";
//    private static final String username="manas@coviam.com";
//    private static final String password="1234";
    SharedPreferences sp;

    {
        RC_SIGN_IN = 9001;
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

        //LOGIN NOW FROM CART
        Intent loginIntent=getIntent();
        String cartLogin=loginIntent.getStringExtra("CartPerson");
        if(cartLogin!=null){
            Snackbar snackbar=Snackbar.make(findViewById(R.id.login_layout),"Login First to Order",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        //REGISTER
        Button register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(Login.this,Register.class);
                startActivity(registerIntent);
            }

        });

        //CUSTOM LOGIN
        Button loginButton= findViewById(R.id.login);
        sp=getSharedPreferences("LoginData",MODE_PRIVATE);
        String check=sp.getString("LoginCheck","false");
        if(check=="false") {

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
                                        editor.putString("LoginCheck","true").apply();
//                                    editor.putBoolean("LoginInMode",true);
                                        editor.commit();
                                        Intent loginIntent = new Intent(Login.this, MainActivity.class);
                                        startActivity(loginIntent);
                                        finish();
                                    } else {
                                        Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), "Invalid Login Details", Snackbar.LENGTH_LONG);
                                        System.out.println(accessTokenDTO.getCheck() + "FAILCHECK");
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
        }
        else{
            Intent LoggedIn=new Intent(Login.this,MainActivity.class);
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
                if(v.getId()==R.id.sign_in_button){
                    signIn();
                }
            }
        });

        //FACEBOOK LOGIN
        facebookloginButton=findViewById(R.id.facebook_login_button);
        facebookloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });


        //SKIP LOGIN
        Button skipSignIn=findViewById(R.id.skip);
        skipSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skipSignInIntent=new Intent(Login.this,MainActivity.class);
                String guestUserId=String.valueOf(Math.random());

                startActivity(skipSignInIntent);
            }
        });
    }

    private void LogIn(){
        callbackManager=CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final AccessToken accessToken=loginResult.getAccessToken();
                final String[] fb_email=new String[1];
                Bundle bundle=new Bundle();
                bundle.putString("fields","id,email,name");
                GraphRequest graphRequest= new GraphRequest(loginResult.getAccessToken(), "me", bundle, null, new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try{
                            System.out.println("EMAIL FB"+response.getJSONObject().getString("email"));
                            System.out.println("NAME FB"+response.getJSONObject().getString("name"));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        String idTokenFB=accessToken.getToken();
                        System.out.println(idTokenFB);
                        Bundle parameters = new Bundle();
                        parameters.putString("fields","first_name,last_name,email,id");
                        GetProductsService getProductsService=RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
                        Call<AccessTokenDTO>call=getProductsService.sendFacebookLogin(idTokenFB);
                        call.enqueue(new Callback<AccessTokenDTO>() {
                            @Override
                            public void onResponse(Call<AccessTokenDTO> call, Response<AccessTokenDTO> response) {
                                String userId=response.body().getUserId();
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putString("UserId",userId).apply();
                                editor.putString("LoginCheck","true").apply();
                                editor.commit();
                                System.out.println("FB REGISTERED");
                                Intent facebookSignInIntent=new Intent(Login.this,MainActivity.class);
                                startActivity(facebookSignInIntent);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<AccessTokenDTO> call, Throwable t) {
                                System.out.println("FAILED FB"+t.getMessage());
                            }
                        });
                    }
                });
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

    }

    //GOOGLE LOGIN PART 2
    private void signIn() {
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
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
        }
        catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
}

