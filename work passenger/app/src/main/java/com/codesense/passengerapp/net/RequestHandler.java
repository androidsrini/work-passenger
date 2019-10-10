package com.codesense.passengerapp.net;

import com.google.gson.JsonElement;

import java.util.HashMap;

import io.reactivex.Observable;

public class RequestHandler {

    private ApiCallInterface apiCallInterface;
    /*private static final RequestHandler requestHandler = new RequestHandler();

    public static RequestHandler GetInstance() {
        return requestHandler;
    }*/

    private HashMap<String, String> postContinueWithMobileParam(String countryDialCode, String mobileNumber) {
        HashMap<String, String> param = new HashMap<>();
        param.put(Constant.COUNTRY_DIAL_CODE_PARAM, countryDialCode);
        param.put(Constant.MOBILE_NUMBER_PARAM, mobileNumber);
        return param;
    }

    public RequestHandler(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    public Observable<JsonElement> fetchCountryList(String apiKey) {
        return apiCallInterface.fetchCountryList(apiKey);
    }

    public Observable<JsonElement> postContinueWithMobileRequest(String apiKey, String countryDialCode, String mobileNumber) {
        return apiCallInterface.postContinueWithMobileRequest(apiKey, postContinueWithMobileParam(countryDialCode, mobileNumber));
    }
}
