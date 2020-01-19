package com.project.sportycart;

import com.google.gson.annotations.SerializedName;

public class Product{

	@SerializedName("noOfSoldUnits")
	private int noOfSoldUnits;

	@SerializedName("productId")
	private int productId;

	@SerializedName("productAttributes")
	private ProductAttributes productAttributes;

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("totalStock")
	private int totalStock;

	@SerializedName("productRating")
	private int productRating;

	@SerializedName("categoryId")
	private int categoryId;

	public void setNoOfSoldUnits(int noOfSoldUnits){
		this.noOfSoldUnits = noOfSoldUnits;
	}

	public int getNoOfSoldUnits(){
		return noOfSoldUnits;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
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

	public void setTotalStock(int totalStock){
		this.totalStock = totalStock;
	}

	public int getTotalStock(){
		return totalStock;
	}

	public void setProductRating(int productRating){
		this.productRating = productRating;
	}

	public int getProductRating(){
		return productRating;
	}

	public void setCategoryId(int categoryId){
		this.categoryId = categoryId;
	}

	public int getCategoryId(){
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