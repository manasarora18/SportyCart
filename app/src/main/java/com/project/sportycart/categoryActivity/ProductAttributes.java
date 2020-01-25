package com.project.sportycart.categoryActivity;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class ProductAttributes{

	@SerializedName("color")
	private String color;

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
			"color = '" + color + '\'' + 
			"}";
		}
}