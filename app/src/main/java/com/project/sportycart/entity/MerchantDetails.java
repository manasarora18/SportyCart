package com.project.sportycart.entity;

import com.google.gson.annotations.SerializedName;

public class MerchantDetails{

	@SerializedName("productId")
	private String productId;

	@SerializedName("merchantId")
	private int merchantId;

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

	public void setMerchantId(int merchantId){
		this.merchantId = merchantId;
	}

	public int getMerchantId(){
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