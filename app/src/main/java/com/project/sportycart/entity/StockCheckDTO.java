package com.project.sportycart.entity;

import com.google.gson.annotations.SerializedName;

public class StockCheckDTO{

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("productId")
	private String productId;

	@SerializedName("merchantId")
	private String merchantId;

	@SerializedName("userId")
	private String userId;

	@SerializedName("status")
	private boolean status;

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

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"StockCheckDTO{" + 
			"quantity = '" + quantity + '\'' + 
			",productId = '" + productId + '\'' + 
			",merchantId = '" + merchantId + '\'' + 
			",userId = '" + userId + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}