package com.project.sportycart;

import com.google.gson.annotations.SerializedName;

public class Product{

	@SerializedName("noOfSoldUnits")
	private String noOfSoldUnits;

	@SerializedName("productId")
	private String productId;

	@SerializedName("productAttributes")
	private ProductAttributes productAttributes;

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("totalStock")
	private String totalStock;

	@SerializedName("productRating")
	private String productRating;

	@SerializedName("categoryId")
	private String categoryId;

	public void setNoOfSoldUnits(String noOfSoldUnits){
		this.noOfSoldUnits = noOfSoldUnits;
	}

	public String getNoOfSoldUnits(){
		return noOfSoldUnits;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setProductAttributes(ProductAttributes productAttributes){
		this.productAttributes = productAttributes;
	}

	public ProductAttributes getProductAttributes(){
		return productAttributes;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTotalStock(String totalStock){
		this.totalStock = totalStock;
	}

	public String getTotalStock(){
		return totalStock;
	}

	public void setProductRating(String productRating){
		this.productRating = productRating;
	}

	public String getProductRating(){
		return productRating;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	@Override
 	public String toString(){
		return 
			"Product{" + 
			"noOfSoldUnits = '" + noOfSoldUnits + '\'' + 
			",productId = '" + productId + '\'' + 
			",productAttributes = '" + productAttributes + '\'' + 
			",imageUrl = '" + imageUrl + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",totalStock = '" + totalStock + '\'' + 
			",productRating = '" + productRating + '\'' + 
			",categoryId = '" + categoryId + '\'' + 
			"}";
		}
}