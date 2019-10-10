package com.codesense.passengerapp.ui.editprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

public class EditProfileActivity extends BaseActivity {

    @Initialize(R.id.rlMain)
    RelativeLayout rlMain;
    @Initialize(R.id.toolbarClose)
    ImageView toolbarClose;
    @Initialize(R.id.imgNameArrow)
    ImageView imgNameArrow;
    @Initialize(R.id.imgMobileArrow)
    ImageView imgMobileArrow;
    @Initialize(R.id.imgEmailArrow)
    ImageView imgEmailArrow;
    @Initialize(R.id.imgPasswordArrow)
    ImageView imgPasswordArrow;
    @Initialize(R.id.rlName)
    RelativeLayout rlName;
    @Initialize(R.id.rlMobile)
    RelativeLayout rlMobile;
    @Initialize(R.id.rlEmail)
    RelativeLayout rlEmail;
    @Initialize(R.id.rlPassword)
    RelativeLayout rlPassword;
    @Initialize(R.id.tvName)
    TextView tvName;
    @Initialize(R.id.tvMobile)
    TextView tvMobile;
    @Initialize(R.id.tvEmail)
    TextView tvEmail;
    @Initialize(R.id.tvPassword)
    TextView tvPassword;
    @Initialize(R.id.tvNameValue)
    TextView tvNameValue;
    @Initialize(R.id.tvMobileValue)
    TextView tvMobileValue;
    @Initialize(R.id.tvEmailValue)
    TextView tvEmailValue;
    @Initialize(R.id.tvPasswordValue)
    TextView tvPasswordValue;
    @Initialize(R.id.tvTitle)
    TextView tvTitle;
    String value;

    @Override
    protected int layoutRes() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBindView.bind(this);
        setDynamicValue();
        tvTitle.setText("Account Details");


    }

    private void setDynamicValue() {
        int topBottomSpace = (int) (screenHeight * 0.0089);
        int imgIconWidth = (int) (screenWidth * 0.075);
        int imgEditHeight = (int) (screenWidth * 0.050);
        int imgEditWidth = (int) (screenWidth * 0.050);
        int imgIconHeight = (int) (screenWidth * 0.075);

        RelativeLayout.LayoutParams imgLayParams = (RelativeLayout.LayoutParams) toolbarClose.getLayoutParams();
        imgLayParams.width = imgIconWidth;
        imgLayParams.height = imgIconHeight;
        toolbarClose.setLayoutParams(imgLayParams);

        RelativeLayout.LayoutParams imgNameArrowLayParams = (RelativeLayout.LayoutParams) imgNameArrow.getLayoutParams();
        imgNameArrowLayParams.width = imgEditWidth;
        imgNameArrowLayParams.height = imgEditHeight;
        imgNameArrow.setLayoutParams(imgNameArrowLayParams);

        RelativeLayout.LayoutParams imgMobileArrowLayParams = (RelativeLayout.LayoutParams) imgMobileArrow.getLayoutParams();
        imgMobileArrowLayParams.width = imgEditWidth;
        imgMobileArrowLayParams.height = imgEditHeight;
        imgMobileArrow.setLayoutParams(imgMobileArrowLayParams);

        RelativeLayout.LayoutParams imgEmailArrowLayParams = (RelativeLayout.LayoutParams) imgEmailArrow.getLayoutParams();
        imgEmailArrowLayParams.width = imgEditWidth;
        imgEmailArrowLayParams.height = imgEditHeight;
        imgEmailArrow.setLayoutParams(imgEmailArrowLayParams);

        RelativeLayout.LayoutParams imgPasswordArrowLayParams = (RelativeLayout.LayoutParams) imgPasswordArrow.getLayoutParams();
        imgPasswordArrowLayParams.width = imgEditWidth;
        imgPasswordArrowLayParams.height = imgEditHeight;
        imgPasswordArrow.setLayoutParams(imgPasswordArrowLayParams);

        RelativeLayout.LayoutParams rlMainLayoutParams = (RelativeLayout.LayoutParams) rlMain.getLayoutParams();
        rlMainLayoutParams.setMargins(topBottomSpace * 3, topBottomSpace * 5, 0, 0);
        rlMain.setLayoutParams(rlMainLayoutParams);

        RelativeLayout.LayoutParams rlMobileLayoutParams = (RelativeLayout.LayoutParams) rlMobile.getLayoutParams();
        rlMobileLayoutParams.setMargins(0, topBottomSpace * 2, 0, 0);
        rlMobile.setLayoutParams(rlMobileLayoutParams);

        RelativeLayout.LayoutParams rlEmailLayoutParams = (RelativeLayout.LayoutParams) rlEmail.getLayoutParams();
        rlEmailLayoutParams.setMargins(0, topBottomSpace * 2, 0, 0);
        rlEmail.setLayoutParams(rlEmailLayoutParams);

        RelativeLayout.LayoutParams rlPasswordLayoutParams = (RelativeLayout.LayoutParams) rlPassword.getLayoutParams();
        rlPasswordLayoutParams.setMargins(0, topBottomSpace * 2, 0, 0);
        rlPassword.setLayoutParams(rlPasswordLayoutParams);

    }

    @Onclick(R.id.toolbarClose)
    public void toolbarClose(View v) {
        finish();
    }

    @Onclick(R.id.rlName)
    public void rlName(View v) {
        value = "name";
        passUpdateView();
    }

    @Onclick(R.id.rlMobile)
    public void rlMobile(View v) {
        value = "mobile";
        passUpdateView();
    }

    @Onclick(R.id.rlEmail)
    public void rlEmail(View v) {
        value = "email";
        passUpdateView();
    }

    @Onclick(R.id.rlPassword)
    public void rlPassword(View v) {
        value = "password";
        passUpdateView();
    }

    public void passUpdateView() {
        Intent intent = new Intent(this, UpdateChooseEditTypeActivity.class);
        intent.putExtra("key", value);
        startActivity(intent);
    }

}
