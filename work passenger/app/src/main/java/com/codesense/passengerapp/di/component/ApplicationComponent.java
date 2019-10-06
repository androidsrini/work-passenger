package com.codesense.passengerapp.di.component;

import android.app.Application;


import com.codesense.passengerapp.base.BaseApplication;
import com.codesense.passengerapp.di.module.ActivityBindingModule;
import com.codesense.passengerapp.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Component(modules = {AndroidSupportInjectionModule.class, ActivityBindingModule.class, NetworkModule.class})
@Singleton
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
