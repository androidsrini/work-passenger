package com.codesense.passengerapp.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;


import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.di.utils.Utility;
import com.codesense.passengerapp.net.RequestHandler;
import com.codesense.passengerapp.ui.launch.LaunchScreenActivity;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    private static final int MINUTE = 1000;
    private static final int INTERVAL_TIME = 1 * MINUTE;

    @Override
    protected int layoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            createHandler();

    }

    private void createHandler() {
        Handler handler = new Handler();
        handler.postDelayed(()->{
            LaunchScreenActivity.start(this);
        }, INTERVAL_TIME);
    }


}
