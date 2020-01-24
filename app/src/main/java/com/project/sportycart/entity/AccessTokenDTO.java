package com.project.sportycart.entity;

import com.google.gson.annotations.SerializedName;

public class AccessTokenDTO{

	@SerializedName("accessToken")
	private String accessToken;

	@SerializedName("userId")
	private String userId;

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	@Override
 	public String toString(){
		return 
			"AccessTokenDTO{" + 
			"accessToken = '" + accessToken + '\'' + 
			",userId = '" + userId + '\'' + 
			"}";
		}
}