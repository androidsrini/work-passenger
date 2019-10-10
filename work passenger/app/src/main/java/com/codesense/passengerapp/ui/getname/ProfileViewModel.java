package com.codesense.passengerapp.ui.getname;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.codesense.passengerapp.net.ApiResponse;
import com.codesense.passengerapp.net.RequestHandler;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {

    private RequestHandler requestHandler;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<ApiResponse> apiResponseMutableLiveData = new MutableLiveData<>();

    @Inject
    public ProfileViewModel(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public MutableLiveData<ApiResponse> getApiResponseMutableLiveData() {
        return apiResponseMutableLiveData;
    }

    public void updatePassengerNameRequest(String apiKey, String firstName, String lastName) {
        disposables.add(requestHandler.updatePassengerNameRequest(apiKey, firstName, lastName)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d->apiResponseMutableLiveData.setValue(ApiResponse.loading()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->apiResponseMutableLiveData.setValue(ApiResponse.success(result)),
                        error->apiResponseMutableLiveData.setValue(ApiResponse.error(error))));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
