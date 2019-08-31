package com.codesense.passengerapp.di.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.codesense.passengerapp.base.BaseApplication;
import com.product.process.helper.Constant;

public class ApiUtility {
    private static final ApiUtility ourInstance = new ApiUtility();

    public static ApiUtility getInstance() {
        return ourInstance;
    }

    private ApiUtility() {
    }

    public String getApiKeyMetaData() {
        try {
            ApplicationInfo appInfo = BaseApplication.getBaseApplication().getPackageManager()
                    .getApplicationInfo(BaseApplication.getBaseApplication().getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null)
                return null;
//                return String.valueOf(appInfo.metaData.getString(Constant.DRIVER_APP_API_KEY));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
