package com.codesense.passengerapp.data;

import com.google.gson.annotations.SerializedName;

public class AgreementResponse{

	@SerializedName("privacy_policy")
	private String privacyPolicy;

	@SerializedName("terms_of_use")
	private String termsOfUse;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setPrivacyPolicy(String privacyPolicy){
		this.privacyPolicy = privacyPolicy;
	}

	public String getPrivacyPolicy(){
		return privacyPolicy;
	}

	public void setTermsOfUse(String termsOfUse){
		this.termsOfUse = termsOfUse;
	}

	public String getTermsOfUse(){
		return termsOfUse;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
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
			"AgreementResponse{" + 
			"privacy_policy = '" + privacyPolicy + '\'' + 
			",terms_of_use = '" + termsOfUse + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}