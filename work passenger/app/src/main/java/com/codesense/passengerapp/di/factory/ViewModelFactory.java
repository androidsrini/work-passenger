package com.codesense.passengerapp.di.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.codesense.passengerapp.net.RequestHandler;
import com.codesense.passengerapp.ui.launch.LaunchScreenViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private RequestHandler requestHandler;

    @Inject
    public ViewModelFactory(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LaunchScreenViewModel.class)) {
            return (T) new LaunchScreenViewModel(requestHandler);
        }
        return null;
    }
}
