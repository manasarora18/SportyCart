package com.project.sportycart.entity;

import com.google.gson.annotations.SerializedName;

public class UserDTO{

	@SerializedName("password")
	private String password;

	@SerializedName("userImageUrl")
	private String userImageUrl;

	@SerializedName("address")
	private String address;

	@SerializedName("phoneNo")
	private long phoneNo;

	@SerializedName("userName")
	private String userName;

	@SerializedName("userId")
	private String userId;

	@SerializedName("email")
	private String email;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setUserImageUrl(String userImageUrl){
		this.userImageUrl = userImageUrl;
	}

	public String getUserImageUrl(){
		return userImageUrl;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setPhoneNo(long phone){
		this.phoneNo = phoneNo;
	}

	public long getPhoneNo(){
		return phoneNo;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"UserDTO{" + 
			"password = '" + password + '\'' + 
			",userImageUrl = '" + userImageUrl + '\'' + 
			",address = '" + address + '\'' + 
			",phoneNo = '" + phoneNo + '\'' +
			",userName = '" + userName + '\'' + 
			",userId = '" + userId + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}