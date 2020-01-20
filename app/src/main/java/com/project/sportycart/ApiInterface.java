package com.project.sportycart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    /*@GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);*/
    @GET("/json/glide.json")
    Call<List<RawData>> getData();



}
