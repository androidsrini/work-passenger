package com.codesense.passengerapp.ui.editprofile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;

public class UpdateChooseEditTypeActivity extends FragmentActivity {


    int screenWidth,screenHeight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_edit_type);
        calculateScreenSize();

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");

        if (value.equalsIgnoreCase("name")){
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, UpdateNameFragment.newInstance(this, screenWidth, screenHeight), "MyFragment").commit();
        }else if (value.equalsIgnoreCase("mobile")){
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, UpdateMobileFragment.newInstance(this, screenWidth, screenHeight), "MyFragment").commit();
        }else if (value.equalsIgnoreCase("email")){
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, UpdateEmailFragment.newInstance(this, screenWidth, screenHeight), "MyFragment").commit();
        } else if (value.equalsIgnoreCase("password")) {
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, UpdatePasswordFragment.newInstance(this, screenWidth, screenHeight), "MyFragment").commit();
        }




    }

    private void calculateScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        assert windowmanager != null;
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

}
