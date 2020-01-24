package com.project.sportycart.retrofit;

import com.project.sportycart.entity.Cart;
import com.project.sportycart.entity.Order;
import com.project.sportycart.entity.StockCheckDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetOrderApis {

    /*@POST("/orderService/order/save/{userId}")
    Call<List<Order>> saveOrder(@Path("userId") String userId);*/

    //Call to checkout API
    @POST("/orderService/order/checkout/{userId}")
    Call<List<StockCheckDTO>> checkoutOrder(@Body List<Cart> orderDetailsDTO, @Path("userId") String userId);

}