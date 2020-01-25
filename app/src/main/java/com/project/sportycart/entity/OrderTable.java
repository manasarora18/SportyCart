package com.project.sportycart.entity;
import com.google.gson.annotations.SerializedName;

public class OrderTable{

    @SerializedName("merchantId")
    private String merchantId;

    @SerializedName("timeStamp")
    private String timeStamp;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("productId")
    private String productId;

    @SerializedName("orderId")
    private String orderId;

    @SerializedName("price")
    private String price;

    @SerializedName("rating")
    private double rating;

    @SerializedName("userId")
    private String userId;

    public void setmerchantId(String merchantId){
        this.merchantId = merchantId;
    }

    public String getmerchantId(){
        return merchantId;
    }

    /*public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp(){
        return timeStamp;
    }*/

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setProductId(String productId){
        this.productId = productId;
    }

    public String getProductId(){
        return productId;
    }

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String getOrderId(){
        return orderId;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public double getRating(){
        return rating;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    @Override
    public String toString(){
        return
                "OrderTable{" +
                        "merchantId = '" + merchantId + '\'' +
                        //",timeStamp = '" + timeStamp + '\'' +
                        ",quantity = '" + quantity + '\'' +
                        ",productId = '" + productId + '\'' +
                        ",orderId = '" + orderId + '\'' +
                        ",price = '" + price + '\'' +
                        ",rating = '" + rating + '\'' +
                        ",userId = '" + userId + '\'' +
                        "}";
    }
}