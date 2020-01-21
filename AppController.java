package com.project.sportycart;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppController extends Application {

    private static Retrofit retrofit;
    static OkHttpClient client;

    public static Retrofit getRetrofit() {
        client = new OkHttpClient().newBuilder().build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("http://172.16.20.131:8086")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
