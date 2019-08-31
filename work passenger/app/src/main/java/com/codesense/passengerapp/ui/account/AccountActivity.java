package com.codesense.passengerapp.ui.account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.ui.business.BusinessActivity;
import com.codesense.passengerapp.ui.drawer.DrawerActivity;

@SuppressLint("Registered")
public class AccountActivity extends DrawerActivity implements View.OnClickListener {

    RelativeLayout rlMain, rlUserDetails, rlBusinessDetails, rlEmergency;
    ImageView imgProfile, imgProfileArrow, imgBusiness, imgBusinessArrow, imgEmergency, imgEmergencyArrow, toolbarClose;
    TextView tvProfileName, tvPhoneNumber, tvEmail, tvBusinessText, tvEmergencyText, tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.activity_account, null, false);
        frameLayout.addView(contentView);
        initialize();
        setDynamicValue();
    }

    private void initialize() {
        rlMain = findViewById(R.id.rlMain);
        rlUserDetails = findViewById(R.id.rlUserDetails);
        imgProfile = findViewById(R.id.imgProfile);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvEmail = findViewById(R.id.tvEmail);
        imgProfileArrow = findViewById(R.id.imgProfileArrow);
        rlBusinessDetails = findViewById(R.id.rlBusinessDetails);
        imgBusiness = findViewById(R.id.imgBusiness);
        tvBusinessText = findViewById(R.id.tvBusinessText);
        imgBusinessArrow = findViewById(R.id.imgBusinessArrow);
        rlEmergency = findViewById(R.id.rlEmergency);
        imgEmergency = findViewById(R.id.imgEmergency);
        tvEmergencyText = findViewById(R.id.tvEmergencyText);
        imgEmergencyArrow = findViewById(R.id.imgEmergencyArrow);
//        toolbarClose = findViewById(R.id.toolbarClose);
//        tvTitle = findViewById(R.id.tvTitle);

        rlBusinessDetails.setOnClickListener(this);
    }

    private void setDynamicValue() {
        int imgIconWidth = (int) (screenWidth * 0.075);
        int imgIconHeight = (int) (screenWidth * 0.075);
        int imgArrowWidth = (int) (screenWidth * 0.075);
        int imgArrowHeight = (int) (screenWidth * 0.075);

        int topBottomSpace = (int) (screenHeight * 0.0089);


        RelativeLayout.LayoutParams rlMainLayParams = (RelativeLayout.LayoutParams) rlMain.getLayoutParams();
        rlMainLayParams.setMargins(0, topBottomSpace * 7, 0, 0);
        rlMain.setLayoutParams(rlMainLayParams);

        RelativeLayout.LayoutParams imgArrowLayParams = (RelativeLayout.LayoutParams) imgProfileArrow.getLayoutParams();
        imgArrowLayParams.width = imgArrowWidth;
        imgArrowLayParams.height = imgArrowHeight;
        imgProfileArrow.setLayoutParams(imgArrowLayParams);

        RelativeLayout.LayoutParams imgBusinessArrowLayParams = (RelativeLayout.LayoutParams) imgBusinessArrow.getLayoutParams();
        imgBusinessArrowLayParams.width = imgArrowWidth;
        imgBusinessArrowLayParams.height = imgArrowHeight;
        imgBusinessArrow.setLayoutParams(imgBusinessArrowLayParams);

        RelativeLayout.LayoutParams imgEmergencyArrowLayParams = (RelativeLayout.LayoutParams) imgEmergencyArrow.getLayoutParams();
        imgEmergencyArrowLayParams.width = imgArrowWidth;
        imgEmergencyArrowLayParams.height = imgArrowHeight;
        imgEmergencyArrow.setLayoutParams(imgEmergencyArrowLayParams);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rlBusinessDetails) {
            Intent intent = new Intent(this, BusinessActivity.class);
            startActivity(intent);
        }
    }
}
