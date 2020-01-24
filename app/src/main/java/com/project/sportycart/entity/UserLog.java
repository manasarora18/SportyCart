package com.project.sportycart.entity;

import com.google.gson.annotations.SerializedName;

public class UserLog{

    @SerializedName("timeStamp")
    private String timeStamp;

    @SerializedName("userId")
    private String userId;

    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp(){
        return timeStamp;
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
                "UserLog{" +
                        "timeStamp = '" + timeStamp + '\'' +
                        ",userId = '" + userId + '\'' +
                        "}";
    }
}