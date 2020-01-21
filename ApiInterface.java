package com.project.sportycart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/product/getAllProducts")
    Call<List<Product>> getAllProducts();



}
