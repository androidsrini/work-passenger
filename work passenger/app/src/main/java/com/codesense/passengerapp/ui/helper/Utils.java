package com.codesense.passengerapp.ui.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final String SOCIAL_ACCOUNT_ACTION = "social_account_item";
    private static final String DRAWABLE = "drawable";



    public static List<TypedArray> getMultiTypedArray(Context context, String key) {
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
    public static int getDrawableResourceID(Context context, String name) {
        return context.getResources().getIdentifier(retrieveIconNameFromPath(name), DRAWABLE, context.getPackageName());
    }

    private static String retrieveIconNameFromPath(String path) {
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

}
