package com.project.sportycart.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if(retrofit==null){
            retrofit=new retrofit2.Retrofit.Builder()
                    .baseUrl("http://172.16.20.131:8082")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
