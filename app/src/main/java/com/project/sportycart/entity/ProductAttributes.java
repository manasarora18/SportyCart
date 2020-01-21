package com.project.sportycart.entity;

import com.google.gson.annotations.SerializedName;

public class ProductAttributes{

	@SerializedName("Company")
	private String company;

	@SerializedName("Color")
	private String color;

	public void setCompany(String company){
		this.company = company;
	}

	public String getCompany(){
		return company;
	}

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	@Override
 	public String toString(){
		return 
			"ProductAttributes{" + 
			"company = '" + company + '\'' + 
			",color = '" + color + '\'' + 
			"}";
		}
}