package com.project.sportycart.entity;

import com.google.gson.annotations.SerializedName;

import retrofit2.Response;

public class RegisterUser{

	@SerializedName("password")
	private String password;

	@SerializedName("userName")
	private String userName;

	@SerializedName("userId")
	private Response<String> userId;

	@SerializedName("email")
	private String email;

	@SerializedName("phoneNo")
	private long phoneNo;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserId(Response<String> userId){
		this.userId = userId;
	}

	public Response<String> getUserId(){
		return userId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setPhoneNo(long phoneNo){
		this.phoneNo = phoneNo;
	}

	public long getPhoneNo(){
		return phoneNo;
	}

	@Override
 	public String toString(){
		return 
			"RegisterUser{" + 
			"password = '" + password + '\'' + 
			",userName = '" + userName + '\'' + 
			",userId = '" + userId + '\'' + 
			",email = '" + email + '\'' + 
			",phoneNo = '" + phoneNo + '\'' + 
			"}";
		}
}