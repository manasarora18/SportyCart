package com.project.sportycart.retrofit;

import com.project.sportycart.categoryActivity.CategoryPageResponse;
import com.project.sportycart.entity.AccessTokenDTO;
import com.project.sportycart.entity.Cart;
import com.project.sportycart.entity.MerchantDetails;
import com.project.sportycart.entity.OrderTable;
import com.project.sportycart.entity.Product;
import com.project.sportycart.entity.RegisterUser;
import com.project.sportycart.entity.UserLog;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetProductsService {
    @GET("/product-service/product/getAllProducts")
    Call<List<Product>> getAllProducts();

    @GET("/product-service/product/showProducts/{categoryId}")
    Call<CategoryPageResponse> getCategoryProducts(@Path("categoryId") Integer categoryId , @Query("pageNo") int page, @Query("pageSize") int size);

    @GET("/search-service/search/{str}")
    Call<List<Product>> getSearchData(@Path("str")String str);

    @GET("/merchant-service/productList/getProductList/{pid}")
    Call <List<MerchantDetails>> getMerchantDetails(@Path("pid") String pid);

    @POST("/order-service/cart/addToCart")
    Call<String> addToCart(@Body Cart cart);

    @POST("/login-service/register")
    Call <AccessTokenDTO> addUser(@Body RegisterUser registerUser);

    @POST("/login-service/login")
    Call<AccessTokenDTO> loginUser(@Body RegisterUser registerUser);

    @GET("/login-service/facebooklogin/{loginAccessTokenDTO}")
    Call<AccessTokenDTO> sendFacebookLogin(@Path("loginAccessTokenDTO") String loginAccessTokenDTO);

    @GET("/login-service/googlelogin/{loginAccessToken}")
    Call<AccessTokenDTO> sendGoogleLogin(@Path("loginAccessToken") String loginAccessToken);

    @POST("/order-service/cart/updateUserOnLogin/{guestUserId}/{userId}")
    Call<Boolean> updateUserLogin(@Path("guestUserId")String guestUserId, @Path("userId")String userId);

    @GET("/orderLog")
    Call<List<OrderTable>> getOrderLog(RegisterUser registerUser);

    @GET("/loginLog")
    Call<List<UserLog>>getLoginLog(RegisterUser registerUser);

}
