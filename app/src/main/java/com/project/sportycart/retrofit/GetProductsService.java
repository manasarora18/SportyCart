package com.project.sportycart.retrofit;

import com.project.sportycart.entity.AccessTokenDTO;
import com.project.sportycart.entity.Cart;
import com.project.sportycart.entity.MerchantDetails;
import com.project.sportycart.entity.OrderTable;
import com.project.sportycart.entity.Product;
import com.project.sportycart.entity.RegisterUser;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetProductsService {
    @GET("/productService/product/getAllProducts")
    Call<List<Product>> getAllProducts();

    @GET("/productService/product/showProducts/{categoryId}")
    Call<List<Product>> getCategoryProducts(@Query("pageNo") int page, @Path("categoryId") Integer categoryId);

    @GET("/searchService/search/{str}")
    Call<List<Product>> getSearchData(@Path("str")String str);

    @GET("/merchantService/productList/getProductList/{pid}")
    Call <List<MerchantDetails>> getMerchantDetails(@Path("pid") String pid);

    @POST("/orderService/cart/addToCart")
    Call<String> addToCart(@Body Cart cart);

    @POST("/register")
    Call <String> addUser(@Body RegisterUser registerUser);

    @POST("/login")
    Call<AccessTokenDTO> loginUser(@Body RegisterUser registerUser);

    @GET("/orderLog")
    Call<List<OrderTable>> getOrderLog(RegisterUser registerUser);

}
