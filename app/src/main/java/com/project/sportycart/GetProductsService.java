package com.project.sportycart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetProductsService {
    @GET("/product/getAllProducts")
    Call<List<Product>> getAllProducts();

    @POST("/getCategoryProducts/{categoryId}")
    Call<List<Product>> getCategoryProducts(@Path("categoryId") String categoryId);

}
