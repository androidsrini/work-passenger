package com.codesense.passengerapp.ui.launch;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.codesense.passengerapp.net.ApiResponse;
import com.codesense.passengerapp.net.Status;
import com.google.gson.JsonElement;

import static com.codesense.passengerapp.net.Status.ERROR;
import static com.codesense.passengerapp.net.Status.LOADING;
import static com.codesense.passengerapp.net.Status.SUCCESS;

public class LaunchScreenApiResponse extends ApiResponse {

    private ServiceType serviceType;

    public ServiceType getServiceType() {
        return serviceType;
    }

    private LaunchScreenApiResponse(Status status, @Nullable JsonElement data, @Nullable Throwable error) {
        super(status, data, error);
    }

    public LaunchScreenApiResponse setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public static LaunchScreenApiResponse loading() {
        return new LaunchScreenApiResponse(LOADING, null, null);
    }

    public static LaunchScreenApiResponse success(@NonNull JsonElement data) {
        return new LaunchScreenApiResponse(SUCCESS, data, null);
    }

    public static LaunchScreenApiResponse error(@NonNull Throwable error) {
        return new LaunchScreenApiResponse(ERROR, null, error);
    }

    public enum ServiceType {
        COUNTRIES, CONTINUE_WITH_MOBILE
    }
}
