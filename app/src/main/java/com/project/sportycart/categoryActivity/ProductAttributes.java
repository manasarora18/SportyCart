package com.project.sportycart.categoryActivity;

import com.google.gson.annotations.SerializedName;

public class ProductAttributes{

	@SerializedName("color")
	private String color;

	@SerializedName("size")
	private String size;

	@SerializedName("material")
	private String material;

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	@Override
	public String toString() {
		return "ProductAttributes{" +
				"color='" + color + '\'' +
				", size='" + size + '\'' +
				", material='" + material + '\'' +
				'}';
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
}