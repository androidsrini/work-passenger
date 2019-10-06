package com.codesense.passengerapp.ui.launch;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.codesense.passengerapp.net.ApiResponse;
import com.codesense.passengerapp.net.RequestHandler;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LaunchScreenViewModel extends ViewModel {

    private RequestHandler requestHandler;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<ApiResponse> apiResponseMutableLiveData = new MutableLiveData<>();

    @Inject
    public LaunchScreenViewModel(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public MutableLiveData<ApiResponse> getApiResponseMutableLiveData() {
        return apiResponseMutableLiveData;
    }

    public void fetchCountryList(String apiKey) {
        disposables.add(requestHandler.fetchCountryList(apiKey)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d->apiResponseMutableLiveData.setValue(ApiResponse.loading()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> apiResponseMutableLiveData.setValue(ApiResponse.success(result)),
                        error -> apiResponseMutableLiveData.setValue(ApiResponse.error(error))));
    }

    public void postContinueWithMobileRequest(String apiKey, String countryCode, String mobileNumber) {
        disposables.add(requestHandler.postContinueWithMobileRequest(apiKey, countryCode, mobileNumber)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d->apiResponseMutableLiveData.setValue(ApiResponse.loading()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> apiResponseMutableLiveData.setValue(ApiResponse.success(result)),
                        error -> apiResponseMutableLiveData.setValue(ApiResponse.error(error))));
    }
}
