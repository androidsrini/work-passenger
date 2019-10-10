package com.codesense.passengerapp.ui.getname;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.di.utils.ApiUtility;
import com.codesense.passengerapp.net.ApiResponse;
import com.codesense.passengerapp.ui.agreement.AgreementActivity;
import com.codesense.passengerapp.ui.helper.Utils;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

import javax.inject.Inject;

public class GetNameActivity extends BaseActivity {

    @Initialize(R.id.toolbar)
    Toolbar toolbar;
    @Initialize(R.id.toolbarClose)
    ImageView toolbarClose;
    @Initialize(R.id.tvNameDes)
    TextView tvNameDes;
    @Initialize(R.id.ll_name)
    LinearLayout ll_name;
    @Initialize(R.id.etFirstName)
    EditText etFirstName;
    @Initialize(R.id.etLastName)
    EditText etLastName;
    @Initialize(R.id.imgNext)
    FloatingActionButton imgNext;
    @Inject
    ProfileViewModel profileViewModel;

    public static void start(Context context) {
        context.startActivity(new Intent(context, GetNameActivity.class));
    }

    private String getEtFirstName() {
        return etFirstName.getText().toString().trim();
    }

    private String getEtLastName() {
        return etLastName.getText().toString().trim();
    }

    private boolean isValidAllFields() {
        boolean isValid = true;
        if (TextUtils.isEmpty(getEtFirstName())) {
            isValid = false;
            Utils.GetInstance().showToastMsg("First name empty");
        } else if (TextUtils.isEmpty(getEtLastName())) {
            isValid = false;
            Utils.GetInstance().showToastMsg("Last name empty");
        }
        return isValid;
    }

    private void apiResponseHandler(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                Utils.GetInstance().showProgressDialog(this);
                break;
            case SUCCESS:
                Utils.GetInstance().dismissDialog();
                AgreementActivity.start(this);
                break;
            case ERROR:
                Utils.GetInstance().dismissDialog();
                break;
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_get_name;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBindView.bind(this);
        profileViewModel.getApiResponseMutableLiveData().observe(this, this::apiResponseHandler);
        setDynamicValue();
    }

    private void setDynamicValue() {
        int imgIconWidth = (int) (screenWidth * 0.075);
        int imgIconHeight = (int) (screenWidth * 0.075);

        int topBottomSpace = (int) (screenHeight * 0.0089);


        RelativeLayout.LayoutParams imgLayParams = (RelativeLayout.LayoutParams) toolbarClose.getLayoutParams();
        imgLayParams.width = imgIconWidth;
        imgLayParams.height = imgIconHeight;
        toolbarClose.setLayoutParams(imgLayParams);

        ConstraintLayout.LayoutParams tvNameDesLayout = (ConstraintLayout.LayoutParams) tvNameDes.getLayoutParams();
        tvNameDesLayout.setMargins(topBottomSpace * 3, topBottomSpace * 5, 0, 0);
        tvNameDes.setLayoutParams(tvNameDesLayout);

        ConstraintLayout.LayoutParams viewLayout = (ConstraintLayout.LayoutParams) ll_name.getLayoutParams();
        viewLayout.setMargins(topBottomSpace * 3, topBottomSpace * 6, topBottomSpace * 3, 0);
        ll_name.setLayoutParams(viewLayout);

        ConstraintLayout.LayoutParams imgNextLayout = (ConstraintLayout.LayoutParams) imgNext.getLayoutParams();
        imgNextLayout.setMargins(0, topBottomSpace * 9, topBottomSpace * 3, 0);
        imgNext.setLayoutParams(imgNextLayout);

    }

    @Onclick(R.id.toolbarClose)
    public void toolbarClose(View v) {
        finish();
    }

    @Onclick(R.id.imgNext)
    public void imgNext(View v) {
        if (isValidAllFields()) {
            profileViewModel.updatePassengerNameRequest(ApiUtility.getInstance().getAccessTokenMetaData(),
                    getEtFirstName(), getEtLastName());
        }
        /*Intent intent = new Intent(this, AgreementActivity.class);
        startActivity(intent);
        finish();*/
    }
}
