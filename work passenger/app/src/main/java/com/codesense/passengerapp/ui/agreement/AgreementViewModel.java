package com.codesense.passengerapp.ui.agreement;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.codesense.passengerapp.net.ApiResponse;
import com.codesense.passengerapp.net.Constant;
import com.codesense.passengerapp.net.RequestHandler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AgreementViewModel extends ViewModel {

    private RequestHandler requestHandler;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<ApiResponse> apiResponseMutableLiveData = new MutableLiveData<>();

    public AgreementViewModel(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public MutableLiveData<ApiResponse> getApiResponseMutableLiveData() {
        return apiResponseMutableLiveData;
    }

    public void fetchAgreementRequest(String apiKey) {
        compositeDisposable.add(requestHandler.fetchAgreementRequest(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d->apiResponseMutableLiveData.setValue(ApiResponse.loading()))
                .subscribe(result->apiResponseMutableLiveData.setValue(ApiResponse.success(result)),
                        error->apiResponseMutableLiveData.setValue(ApiResponse.error(error))));
    }

    public void setAcceptConditionsRequest(String apiKey) {
        compositeDisposable.add(requestHandler.setAcceptConditionsRequest(apiKey, Constant.ACCEPTED)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d->apiResponseMutableLiveData.setValue(ApiResponse.loading()))
                .subscribe(result->apiResponseMutableLiveData.setValue(ApiResponse.success(result)),
                        error->apiResponseMutableLiveData.setValue(ApiResponse.error(error))));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
