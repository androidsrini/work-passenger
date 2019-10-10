package com.codesense.passengerapp.ui.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import android.widget.Toast;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final String SOCIAL_ACCOUNT_ACTION = "social_account_item";
    private static final String DRAWABLE = "drawable";
    private static final Object LOCK = new Object();
    private static Utils utils;
    private ProgressDialog progressDialog;

    public static Utils GetInstance() {
        synchronized (LOCK) {
            if (null == utils) {
                utils = new Utils();
            }
        }
        return utils;
    }

    private Utils() {

    }

    private String retrieveIconNameFromPath(String path) {
        String[] filePath = path.split(File.separator);
        int length = filePath.length;
        int nameLength = 0;
        String[] nameArray = new String[0];
        String fullName = (length > 1 ? filePath[length - 1] : null);
        if (fullName != null) {
            nameArray = !TextUtils.isEmpty(fullName) ? fullName.split("\\.") : new String[]{};
            nameLength = nameArray.length;
        }
        return (nameLength > 1 ? nameArray[0] : "");
    }

    public List<TypedArray> getMultiTypedArray(Context context, String key) {
        List<TypedArray> array = new ArrayList<>();
        try {
            //Class<R.array> res = R.array.class;
            //Field field;
            int counter = 0;
            int fieldID = -1;
            do {
                //field = res.getField(key + "_" + counter);
                //details = res.getIdentifier(strItem, "array", getPackageName());
                //array.add(context.getResources().obtainTypedArray(field.getInt(null)));
                fieldID = context.getResources().getIdentifier(key + "_" + counter, "array", context.getPackageName());
                array.add(context.getResources().obtainTypedArray(fieldID));
                counter++;
            } while (fieldID != -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
    public int getDrawableResourceID(Context context, String name) {
        return context.getResources().getIdentifier(retrieveIconNameFromPath(name), DRAWABLE, context.getPackageName());
    }

    public void showProgressDialog(@UiThread Context context) {
        progressDialog = ProgressDialog.show(context, null, context.getString(R.string.loading), false);
    }

    @UiThread
    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * This method to disply toast message based in UI context.
     * @param context
     * @param msg
     */
    @UiThread
    public void showToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method to show the toast message based on the app context
     * @param msg string
     */
    public void showToastMsg(String msg) {
        Toast.makeText(BaseApplication.getBaseApplication(), msg, Toast.LENGTH_SHORT).show();
    }
}
