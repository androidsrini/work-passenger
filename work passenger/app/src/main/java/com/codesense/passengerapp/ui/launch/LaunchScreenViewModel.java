package com.codesense.passengerapp.ui.launch;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.codesense.passengerapp.net.RequestHandler;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LaunchScreenViewModel extends ViewModel {

    private RequestHandler requestHandler;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<LaunchScreenApiResponse> apiResponseMutableLiveData = new MutableLiveData<>();

    @Inject
    public LaunchScreenViewModel(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public MutableLiveData<LaunchScreenApiResponse> getApiResponseMutableLiveData() {
        return apiResponseMutableLiveData;
    }

    public void fetchCountryList(String apiKey) {
        disposables.add(requestHandler.fetchCountryList(apiKey)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d->apiResponseMutableLiveData.setValue(LaunchScreenApiResponse.loading()
                        .setServiceType(LaunchScreenApiResponse.ServiceType.COUNTRIES)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> apiResponseMutableLiveData.setValue(LaunchScreenApiResponse.success(result)
                                .setServiceType(LaunchScreenApiResponse.ServiceType.COUNTRIES)),
                        error -> apiResponseMutableLiveData.setValue(LaunchScreenApiResponse.error(error)
                                .setServiceType(LaunchScreenApiResponse.ServiceType.COUNTRIES))));
    }

    public void postContinueWithMobileRequest(String apiKey, String deviceId, String countryCode, String mobileNumber) {
        disposables.add(requestHandler.postContinueWithMobileRequest(apiKey, deviceId, countryCode, mobileNumber)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d->apiResponseMutableLiveData.setValue(LaunchScreenApiResponse.loading()
                        .setServiceType(LaunchScreenApiResponse.ServiceType.CONTINUE_WITH_MOBILE)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result-> apiResponseMutableLiveData.setValue(LaunchScreenApiResponse.success(result)
                                .setServiceType(LaunchScreenApiResponse.ServiceType.CONTINUE_WITH_MOBILE)),
                        error -> apiResponseMutableLiveData.setValue(LaunchScreenApiResponse.error(error)
                                .setServiceType(LaunchScreenApiResponse.ServiceType.CONTINUE_WITH_MOBILE))));
    }
}
