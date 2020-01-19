package com.project.sportycart;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if(retrofit==null){
            retrofit=new retrofit2.Retrofit.Builder()
                    .baseUrl("https://api.androidhive.info")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
