package com.project.sportycart.retrofit;

import com.project.sportycart.entity.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetCartApis {

    @GET("cart/getCart/{userId}")
    Call<List<Cart>> getAllCartItems(@Path("userId") String userId);


    /*@POST("order/save/{userId}")
    Call<>*/


}
