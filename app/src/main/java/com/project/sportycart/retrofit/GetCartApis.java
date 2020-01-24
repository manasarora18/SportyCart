package com.project.sportycart.retrofit;

import com.project.sportycart.entity.Cart;

import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetCartApis {

    //API to get cart details of the logged in user
    @GET("/orderService/cart/getCart/{userId}")
    Call<List<Cart>> getAllCartItems(@Path("userId") String userId);

    //API to increment or decrement cart quantity
    @GET("/orderService/cart/cartIncrement/{productId}/{userId}/{quantity}")
    Call<Boolean> updateCartQuantity(@Path("productId") String productId, @Path("userId") String userId, @Path("quantity") int quantity);

    @GET("/orderService/cart/deleteCartRow/{userId}/{merchantId}/{productId}")
    Call<Boolean> deleteCartItem(@Path("userId") String userId, @Path("merchantId") String merchantId, @Path("productId") String productId);

}
