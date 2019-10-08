package com.codesense.passengerapp.data;

import com.google.gson.annotations.SerializedName;

public class ContinueWithMobileNumberResponse{

	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("country_dial_code")
	private String countryDialCode;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("invite_code")
	private String inviteCode;

	@SerializedName("message")
	private String message;

	@SerializedName("mobile_number")
	private String mobileNumber;

	@SerializedName("status")
	private int status;

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setCountryDialCode(String countryDialCode){
		this.countryDialCode = countryDialCode;
	}

	public String getCountryDialCode(){
		return countryDialCode;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setInviteCode(String inviteCode){
		this.inviteCode = inviteCode;
	}

	public String getInviteCode(){
		return inviteCode;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setMobileNumber(String mobileNumber){
		this.mobileNumber = mobileNumber;
	}

	public String getMobileNumber(){
		return mobileNumber;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ContinueWithMobileNumberResponse{" + 
			"access_token = '" + accessToken + '\'' + 
			",country_dial_code = '" + countryDialCode + '\'' + 
			",user_id = '" + userId + '\'' + 
			",invite_code = '" + inviteCode + '\'' + 
			",message = '" + message + '\'' + 
			",mobile_number = '" + mobileNumber + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}