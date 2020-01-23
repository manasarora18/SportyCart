package com.project.sportycart.retrofit;

import com.project.sportycart.entity.Cart;
import com.project.sportycart.entity.MerchantDetails;
import com.project.sportycart.entity.Product;
import com.project.sportycart.entity.RegisterUser;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetProductsService {
    @GET("/productService/product/getAllProducts")
    Call<List<Product>> getAllProducts();

    @GET("/productService/product/showProducts/{categoryId}")
    Call<List<Product>> getCategoryProducts(@Path("categoryId") Integer categoryId);

    @GET("/searchService/search/{str}")
    Call<List<Product>> getSearchData(@Path("str")String str);

    @POST("/productList/getProductList/{pid}")
    Call <List<MerchantDetails>> getMerchantDetails(@Path("pid") String pid);

    @POST("/orderService/cart/addToCart")
    Call<String> addToCart(@Body Cart cart);

    @POST("/register")
    Call <String> addUser(@Body RegisterUser registerUser);

    @POST("/login")
    Call<String> loginUser(@Body RegisterUser registerUser);

}
