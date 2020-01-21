package com.project.sportycart.entity;

import com.google.gson.annotations.SerializedName;

public class ProductAttributes{

	@SerializedName("color")
	private String color;

	@SerializedName("material")
	private String material;

	@SerializedName("size")
	private String size;

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setMaterial(String material){
		this.material = material;
	}

	public String getMaterial(){
		return material;
	}

	public void setSize(String size){
		this.size = size;
	}

	public String getSize(){
		return size;
	}

	@Override
 	public String toString(){
		return 
			"ProductAttributes{" + 
			"color = '" + color + '\'' + 
			",material = '" + material + '\'' + 
			",size = '" + size + '\'' + 
			"}";
		}
}