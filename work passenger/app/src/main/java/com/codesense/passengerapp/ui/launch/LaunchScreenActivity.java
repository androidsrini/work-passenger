package com.codesense.passengerapp.ui.launch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.ui.socialaccount.SocialAccountActivity;
import com.codesense.passengerapp.ui.verifymobile.VerifyMobileActivity;
import com.hbb20.CountryCodePicker;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

public class LaunchScreenActivity extends BaseActivity {

    @Initialize(R.id.country)
    CountryCodePicker ccp;
    @Initialize(R.id.tvContinueMobile)
    TextView tvContinueMobile;
    @Initialize(R.id.view1)
    View view1;
    @Initialize(R.id.view2)
    View view2;
    @Initialize(R.id.ll_mobile_number)
    LinearLayout ll_mobile_number;
    @Initialize(R.id.etMobileNumber)
    EditText etMobileNumber;
    @Initialize(R.id.tvSocialConnect)
    TextView tvSocialConnect;

    @Override
    protected int layoutRes() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBindView.bind(this);
        setDynamicValue();
        functionality();

        ccp.setClickable(false);

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, LaunchScreenActivity.class));
    }

    private void functionality() {

        etMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMobileNumber.getText().toString().trim().length() == 10) {
                    VerifyMobileActivity.start(LaunchScreenActivity.this,etMobileNumber.getText().toString().trim());
                    etMobileNumber.setText("");
                }

            }
        });
    }

    private void setDynamicValue() {

        int imgIconWidth = (int) (screenWidth * 0.075);
        int imgIconHeight = (int) (screenWidth * 0.075);


        int topBottomSpace = (int) (screenHeight * 0.0089);

        RelativeLayout.LayoutParams ll_nameLayoutParams = (RelativeLayout.LayoutParams) tvContinueMobile.getLayoutParams();
        ll_nameLayoutParams.setMargins(topBottomSpace * 3, topBottomSpace * 4, topBottomSpace * 3, 0);
        tvContinueMobile.setLayoutParams(ll_nameLayoutParams);

        RelativeLayout.LayoutParams view1LayoutParams = (RelativeLayout.LayoutParams) view1.getLayoutParams();
        view1LayoutParams.setMargins(0, topBottomSpace * 2, 0, 0);
        view1.setLayoutParams(view1LayoutParams);

        RelativeLayout.LayoutParams view2LayoutParams = (RelativeLayout.LayoutParams) view2.getLayoutParams();
        view2LayoutParams.setMargins(0, 0, 0, 0);
        view2.setLayoutParams(view2LayoutParams);


        etMobileNumber.setPadding(topBottomSpace * 2, 0, 0, 0);

        RelativeLayout.LayoutParams tvSocialConnectLayoutParams = (RelativeLayout.LayoutParams) tvSocialConnect.getLayoutParams();
        tvSocialConnectLayoutParams.setMargins(topBottomSpace * 3, topBottomSpace * 3, 0, 0);
        tvSocialConnect.setLayoutParams(tvSocialConnectLayoutParams);


    }

    @Onclick(R.id.tvSocialConnect)
    public void tvSocialConnect(View v) {
        SocialAccountActivity.start(this);
    }


/*public void onCountryPickerClick(View view) {
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                //Alert.showMessage(RegistrationActivity.this, ccp.getSelectedCountryCodeWithPlus());
              String  selected_country_code = ccp.getSelectedCountryCodeWithPlus();
            }
        });
    }*/
}
