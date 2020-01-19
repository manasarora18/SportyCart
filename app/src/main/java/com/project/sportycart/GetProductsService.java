package com.project.sportycart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetProductsService {
    @GET("/json/glide.json")
    Call<List<Product>> getAllProducts();
}
