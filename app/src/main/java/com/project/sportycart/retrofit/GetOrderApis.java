package com.project.sportycart.retrofit;

import com.project.sportycart.entity.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetOrderApis {

    @POST("/order/save/{userId}")
    Call<List<Order>> saveOrder(@Path("userId") String userId);

    @POST("/order/checkout/{userId}")
    Call<List<Order>> checkoutOrder(@Path("userId") String userId);

}
