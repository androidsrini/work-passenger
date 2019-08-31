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


}
