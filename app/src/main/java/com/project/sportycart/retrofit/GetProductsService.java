package com.project.sportycart.retrofit;

import com.project.sportycart.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetProductsService {
    @GET("/product/getAllProducts")
    Call<List<Product>> getAllProducts();

    @GET("/product/showProducts/{categoryId}")
    Call<List<Product>> getCategoryProducts(@Path("categoryId") Integer categoryId);

}
