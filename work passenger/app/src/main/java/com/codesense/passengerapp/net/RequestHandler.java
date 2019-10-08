package com.codesense.passengerapp.net;

import com.codesense.passengerapp.localstoreage.AppSharedPreference;
import com.google.gson.JsonElement;

import java.util.HashMap;

import io.reactivex.Observable;

public class RequestHandler {

    private ApiCallInterface apiCallInterface;
    private AppSharedPreference appSharedPreference;
    /*private static final RequestHandler requestHandler = new RequestHandler();

    public static RequestHandler GetInstance() {
        return requestHandler;
    }*/

    private HashMap<String, String> postContinueWithMobileParam(String deviceId, String countryDialCode, String mobileNumber) {
        HashMap<String, String> param = new HashMap<>();
        param.put(Constant.COUNTRY_DIAL_CODE_PARAM, countryDialCode);
        param.put(Constant.MOBILE_NUMBER_PARAM, mobileNumber);
        param.put(Constant.DEVICE_ID_PARAM, deviceId);
        return param;
    }

    private HashMap<String, String> getUserIdParam() {
        HashMap<String, String> param = new HashMap<>();
        param.put(Constant.USER_ID_PARAM, appSharedPreference.getUserID());
        /*param.put(Constant.ACCESS_TOKE_PARAM, appSharedPreference.getAccessTokenKey());*/
        return param;
    }

    private HashMap<String, String> getOtpParam(String otp) {
        HashMap<String, String> param = getUserIdParam();
        param.put(Constant.OTP_PARAM, otp);
        return param;
    }

    private HashMap<String, String> getNameParam(String firstName, String lastName) {
        HashMap<String, String> param = getUserIdParam();
        param.put(Constant.FIRST_NAME_PARAM, firstName);
        param.put(Constant.LAST_NAME_PARAM, lastName);
        return param;
    }

    private HashMap<String, String> getAcceptConditionParam(String accept) {
        HashMap<String, String> param = getUserIdParam();
        param.put(Constant.ACCEPT_CONDITIONS_PARAM, accept);
        return param;
    }

    private HashMap<String, String> getChangeMobileNumberParam(String dialCode, String mobileNumber) {
        HashMap<String, String> param = getUserIdParam();
        param.put(Constant.COUNTRY_DIAL_CODE_PARAM, dialCode);
        param.put(Constant.MOBILE_NUMBER_PARAM, mobileNumber);
        return param;
    }

    public RequestHandler(ApiCallInterface apiCallInterface, AppSharedPreference appSharedPreference) {
        this.apiCallInterface = apiCallInterface;
        this.appSharedPreference = appSharedPreference;
    }

    public Observable<JsonElement> fetchCountryList(String apiKey) {
        return apiCallInterface.fetchCountryList(apiKey);
    }

    public Observable<JsonElement> postContinueWithMobileRequest(String apiKey, String deviceId, String countryDialCode, String mobileNumber) {
        return apiCallInterface.postContinueWithMobileRequest(apiKey, postContinueWithMobileParam(deviceId, countryDialCode, mobileNumber));
    }

    public Observable<JsonElement> sendOtpRequest(String apiKey) {
        return apiCallInterface.sendOtpRequest(apiKey, appSharedPreference.getAccessTokenKey(), getUserIdParam());
    }

    public Observable<JsonElement> verifyOtpRequest(String apiKey, String otp) {
        return apiCallInterface.verifyOtpRequest(apiKey, appSharedPreference.getAccessTokenKey(), getOtpParam(otp));
    }

    public Observable<JsonElement> updatePassengerNameRequest(String apiKey, String firstName, String lastName) {
        return apiCallInterface.updatePassengerNameRequest(apiKey, appSharedPreference.getAccessTokenKey(),
                getNameParam(firstName, lastName));
    }

    public Observable<JsonElement> fetchAgreementRequest(String apiKey) {
        return apiCallInterface.fetchAgreementRequest(apiKey, appSharedPreference.getAccessTokenKey());
    }

    public Observable<JsonElement> setAcceptConditionsRequest(String apiKey, String acceptCondition) {
        return apiCallInterface.setAcceptConditionsRequest(apiKey, appSharedPreference.getAccessTokenKey(),
                getAcceptConditionParam(acceptCondition));
    }

    public Observable<JsonElement> changeMobileNumberRequest(String apiKey, String dialCode, String mobileNumber) {
        return apiCallInterface.changeMobileNumberRequest(apiKey, appSharedPreference.getAccessTokenKey(),
                getChangeMobileNumberParam(dialCode, mobileNumber));
    }

    public Observable<JsonElement> signoutRequest(String apiKey) {
        return apiCallInterface.signoutRequest(apiKey, appSharedPreference.getAccessTokenKey(),
                getUserIdParam());
    }
}
