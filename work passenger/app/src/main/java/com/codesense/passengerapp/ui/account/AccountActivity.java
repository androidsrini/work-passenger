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
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.ui.business.BusinessActivity;
import com.codesense.passengerapp.ui.drawer.DrawerActivity;
import com.codesense.passengerapp.ui.editprofile.EditProfileActivity;
import com.codesense.passengerapp.ui.emergency.EmergencyActivity;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

@SuppressLint("Registered")
public class AccountActivity extends BaseActivity {

    @Initialize(R.id.rlMain)
    RelativeLayout rlMain;
    @Initialize(R.id.rlUserDetails)
    RelativeLayout rlUserDetails;
    @Initialize(R.id.rlBusinessDetails)
    RelativeLayout rlBusinessDetails;
    @Initialize(R.id.rlEmergency)
    RelativeLayout rlEmergency;
    @Initialize(R.id.imgProfile)
    ImageView imgProfile;
    @Initialize(R.id.imgProfileArrow)
    ImageView imgProfileArrow;
    @Initialize(R.id.imgBusiness)
    ImageView imgBusiness;
    @Initialize(R.id.imgBusinessArrow)
    ImageView imgBusinessArrow;
    @Initialize(R.id.imgEmergency)
    ImageView imgEmergency;
    @Initialize(R.id.imgEmergencyArrow)
    ImageView imgEmergencyArrow;
    @Initialize(R.id.toolbarClose)
    ImageView toolbarClose;
    @Initialize(R.id.tvProfileName)
    TextView tvProfileName;
    @Initialize(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @Initialize(R.id.tvEmail)
    TextView tvEmail;
    @Initialize(R.id.tvBusinessText)
    TextView tvBusinessText;
    @Initialize(R.id.tvEmergencyText)
    TextView tvEmergencyText;
    @Initialize(R.id.tvTitle)
    TextView tvTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBindView.bind(this);
        setDynamicValue();

        tvTitle.setText("Your Account");
    }
    @Override
    protected int layoutRes() {
        return R.layout.activity_account;
    }

    private void setDynamicValue() {
        int imgIconWidth = (int) (screenWidth * 0.075);
        int imgIconHeight = (int) (screenWidth * 0.075);
        int imgArrowWidth = (int) (screenWidth * 0.075);
        int imgArrowHeight = (int) (screenWidth * 0.075);

        int topBottomSpace = (int) (screenHeight * 0.0089);
        RelativeLayout.LayoutParams imgLayParams = (RelativeLayout.LayoutParams) toolbarClose.getLayoutParams();
        imgLayParams.width = imgIconWidth;
        imgLayParams.height = imgIconHeight;
        toolbarClose.setLayoutParams(imgLayParams);

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


    @Onclick(R.id.toolbarClose)
    public void toolbarClose(View v) {
        finish();
    }

    @Onclick(R.id.rlBusinessDetails)
    public void rlBusinessDetails(View v) {
        Intent intent = new Intent(this, BusinessActivity.class);
        startActivity(intent);
    }

    @Onclick(R.id.rlUserDetails)
    public void rlUserDetails(View v) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
    @Onclick(R.id.rlEmergency)
    public void rlEmergency(View v) {
        Intent intent = new Intent(this, EmergencyActivity.class);
        startActivity(intent);
    }
}
