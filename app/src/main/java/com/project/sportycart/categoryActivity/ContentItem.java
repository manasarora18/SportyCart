package com.project.sportycart.categoryActivity;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class ContentItem{

	@SerializedName("noOfSoldUnits")
	private int noOfSoldUnits;

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

	@SerializedName("productRating")
	private double productRating;

	@SerializedName("categoryId")
	private int categoryId;

	public void setNoOfSoldUnits(int noOfSoldUnits){
		this.noOfSoldUnits = noOfSoldUnits;
	}

	public int getNoOfSoldUnits(){
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

	public void setProductRating(double productRating){
		this.productRating = productRating;
	}

	public double getProductRating(){
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
			"ContentItem{" + 
			"noOfSoldUnits = '" + noOfSoldUnits + '\'' + 
			",productId = '" + productId + '\'' + 
			",productAttributes = '" + productAttributes + '\'' + 
			",imageUrl = '" + imageUrl + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",productRating = '" + productRating + '\'' + 
			",categoryId = '" + categoryId + '\'' + 
			"}";
		}
}