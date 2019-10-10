package com.codesense.passengerapp.base;


import com.codesense.passengerapp.di.component.ApplicationComponent;
import com.codesense.passengerapp.di.component.DaggerApplicationComponent;
import com.codesense.passengerapp.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

@Singleton
public class BaseApplication extends DaggerApplication {

    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        ApplicationComponent component = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule(this)).build();
        component.inject(this);
        return component;
    }

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }
}
