package com.project.sportycart.entity;

import com.google.gson.annotations.SerializedName;
import com.project.sportycart.adapter.ProductAdapter;

public class MerchantDetails {

	@SerializedName("productId")
	private String productId;

	@SerializedName("merchantId")
	private String merchantId;

	@SerializedName("price")
	private double price;

	@SerializedName("rating")
	private double rating;

	@SerializedName("stock")
	private int stock;

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}

	public void setRating(double rating){
		this.rating = rating;
	}

	public double getRating(){
		return rating;
	}

	public void setStock(int stock){
		this.stock = stock;
	}

	public int getStock(){
		return stock;
	}

	@Override
 	public String toString(){
		return 
			"MerchantDetails{" + 
			"productId = '" + productId + '\'' + 
			",merchantId = '" + merchantId + '\'' + 
			",price = '" + price + '\'' + 
			",rating = '" + rating + '\'' + 
			",stock = '" + stock + '\'' + 
			"}";
		}
}