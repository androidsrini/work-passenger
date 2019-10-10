package com.codesense.passengerapp.net;

import com.google.gson.JsonElement;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiCallInterface {

    //@Headers(Constant.API_AUTH_KEY_WITH_VALUE)
    @GET(WebserviceUrls.API_INFO)
    Observable<JsonElement> getApiInfoRequest(@Header(Constant.API_AUTH_KEY_PARAM) String apiKey);

    @GET(WebserviceUrls.COUNTRIES)
    Observable<JsonElement> fetchCountryList(@Header(Constant.API_AUTH_KEY_PARAM) String apiKey);

    @POST(WebserviceUrls.CONTINUE_WITH_MOBILE)
    @FormUrlEncoded
    Observable<JsonElement> postContinueWithMobileRequest(@Header(Constant.API_AUTH_KEY_PARAM) String apiKey,
                                                          @FieldMap HashMap<String, String> param);

    @POST(WebserviceUrls.SEND_OTP)
    @FormUrlEncoded
    Observable<JsonElement> sendOtpRequest(@Header(Constant.API_AUTH_KEY_PARAM) String apiKey,
                                           @Header(Constant.ACCESS_TOKE_PARAM) String accessToken,
                                                          @FieldMap HashMap<String, String> param);


    @POST(WebserviceUrls.VERIFY_OTP)
    @FormUrlEncoded
    Observable<JsonElement> verifyOtpRequest(@Header(Constant.API_AUTH_KEY_PARAM) String apiKey,
                                             @Header(Constant.ACCESS_TOKE_PARAM) String accessToken,
                                                          @FieldMap HashMap<String, String> param);


    @POST(WebserviceUrls.UPDATE_PASSENGER_NAME)
    @FormUrlEncoded
    Observable<JsonElement> updatePassengerNameRequest(@Header(Constant.API_AUTH_KEY_PARAM) String apiKey,
                                             @Header(Constant.ACCESS_TOKE_PARAM) String accessToken,
                                                          @FieldMap HashMap<String, String> param);

    @GET(WebserviceUrls.PASSENGER_AGREEMENT)
    Observable<JsonElement> fetchAgreementRequest(@Header(Constant.API_AUTH_KEY_PARAM) String apiKey,
                                             @Header(Constant.ACCESS_TOKE_PARAM) String accessToken);

    @POST(WebserviceUrls.ACCEPT_CONDITIONS)
    @FormUrlEncoded
    Observable<JsonElement> setAcceptConditionsRequest(@Header(Constant.API_AUTH_KEY_PARAM) String apiKey,
                                             @Header(Constant.ACCESS_TOKE_PARAM) String accessToken,
                                                       @FieldMap HashMap<String, String> param);

    @POST(WebserviceUrls.CHANGE_MOBILE_NUMBER)
    @FormUrlEncoded
    Observable<JsonElement> changeMobileNumberRequest(@Header(Constant.API_AUTH_KEY_PARAM) String apiKey,
                                             @Header(Constant.ACCESS_TOKE_PARAM) String accessToken,
                                                       @FieldMap HashMap<String, String> param);

    @POST(WebserviceUrls.SIGNOUT)
    @FormUrlEncoded
    Observable<JsonElement> signoutRequest(@Header(Constant.API_AUTH_KEY_PARAM) String apiKey,
                                             @Header(Constant.ACCESS_TOKE_PARAM) String accessToken,
                                                       @FieldMap HashMap<String, String> param);
}
