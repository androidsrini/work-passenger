package com.codesense.passengerapp.ui.editmobilenumber;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.di.utils.ApiUtility;
import com.codesense.passengerapp.localstoreage.DatabaseClient;
import com.codesense.passengerapp.net.ApiResponse;
import com.codesense.passengerapp.net.RequestHandler;
import com.codesense.passengerapp.ui.helper.Utils;
import com.codesense.passengerapp.ui.verifymobile.VerifyMobileActivity;
import com.hbb20.CountryCodePicker;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class EditMobileNumberActivity extends BaseActivity {

    private static final String TAG = EditMobileNumberActivity.class.getName();
    private static final String PHONE_NUMBER_ARG = "PhoneNumber";
    private static final String USER_ID_ARG = "UserID";
    @Initialize(R.id.toolbar)
    Toolbar toolbar;
    @Initialize(R.id.toolbarClose)
    ImageView toolbarClose;
    @Initialize(R.id.tvMobileNumber)
    TextView tvMobileNumber;
    @Initialize(R.id.ll_mobile_number)
    LinearLayout ll_mobile_number;
    @Initialize(R.id.country)
    CountryCodePicker country;
    @Initialize(R.id.etMobileNumber)
    EditText etMobileNumber;
    @Initialize(R.id.llNext)
    LinearLayout llNext;
    @Initialize(R.id.tvDesMobile)
    TextView tvDesMobile;
    @Initialize(R.id.imgNext)
    ImageButton imgNext;
    @Initialize(R.id.view2)
    View view2;
    protected @Inject
    RequestHandler requestHandler;
    private String userID, phoneNumber, phoneNo;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static void start(Context context) {
        context.startActivity(new Intent(context, EditMobileNumberActivity.class));
    }

    public static void start(Context context, String userID, String phoneNumber) {
        Intent intent = new Intent(context, EditMobileNumberActivity.class);
        intent.putExtra(USER_ID_ARG, userID);
        intent.putExtra(PHONE_NUMBER_ARG, phoneNumber);
        context.startActivity(intent);
    }

    private void fetchCountryInDataBase(String countryName) {
        compositeDisposable.add(DatabaseClient.getInstance(this).getAppDatabase().countryDao().findByCountryName(countryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.d(TAG, "Country name: " + result);
                    changeMobileNumberRequest(result.countryDialCode);
                }, error -> {
                    Log.d(TAG, "Country name error: " + error);
                }));
    }

    private void changeMobileNumberRequest(String dialCode) {
        compositeDisposable.add(requestHandler.changeMobileNumberRequest(ApiUtility.getInstance().getAccessTokenMetaData(),
                dialCode, checkPhoneNumber())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d->apiResponseHandler(ApiResponse.loading()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->apiResponseHandler(ApiResponse.success(result)),
                        error->apiResponseHandler(ApiResponse.error(error))));
    }

    private void apiResponseHandler(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                Utils.GetInstance().showProgressDialog(this);
                break;
            case SUCCESS:
                Utils.GetInstance().dismissDialog();
                VerifyMobileActivity.start(EditMobileNumberActivity.this, etMobileNumber.getText().toString().trim(), false);
                break;
            case ERROR:
                Utils.GetInstance().dismissDialog();
                break;
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_edit_mobile_number;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBindView.bind(this);
        setUI();
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

        ConstraintLayout.LayoutParams tvMobileNumberLayParams = (ConstraintLayout.LayoutParams) tvMobileNumber.getLayoutParams();
        tvMobileNumberLayParams.setMargins(topBottomSpace * 3, topBottomSpace * 6, topBottomSpace * 2, 0);
        tvMobileNumber.setLayoutParams(tvMobileNumberLayParams);

        ConstraintLayout.LayoutParams ll_mobile_numberLayParams = (ConstraintLayout.LayoutParams) ll_mobile_number.getLayoutParams();
        ll_mobile_numberLayParams.setMargins(topBottomSpace * 3, topBottomSpace * 5, topBottomSpace * 2, 0);
        ll_mobile_number.setLayoutParams(ll_mobile_numberLayParams);

        ConstraintLayout.LayoutParams view2LayParams = (ConstraintLayout.LayoutParams) view2.getLayoutParams();
        view2LayParams.setMargins(topBottomSpace * 3, 0, topBottomSpace * 2, 0);
        view2.setLayoutParams(view2LayParams);


        ConstraintLayout.LayoutParams llNextLayParams = (ConstraintLayout.LayoutParams) llNext.getLayoutParams();
        llNextLayParams.setMargins(topBottomSpace * 3, topBottomSpace * 8, topBottomSpace * 2, 0);
        llNext.setLayoutParams(llNextLayParams);

        etMobileNumber.setPadding(topBottomSpace, 0, topBottomSpace, 0);

    }

    @Onclick(R.id.imgNext)
    public void imgNext(View v) {
        if (checkPhoneNumber() != null) {
            fetchCountryInDataBase(country.getSelectedCountryName());
//            changeMobileNumberRequest(userID, checkPhoneNumber());
        } else {
            Toast.makeText(this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Onclick(R.id.toolbarClose)
    public void toolbarClose(View v) {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private String getPhoneNumber() {
        if (null != getIntent() && TextUtils.isEmpty(phoneNumber)) {
            phoneNumber = getIntent().getStringExtra(PHONE_NUMBER_ARG);
        }
        return phoneNumber;
    }

    private String checkPhoneNumber() {
        if (!TextUtils.isEmpty(etMobileNumber.getText().toString().trim())) {
            if (etMobileNumber.getText().toString().trim().length() >= 10) {
                phoneNo = etMobileNumber.getText().toString().trim();
            }
        }
        return phoneNo;
    }

    private String getUserId() {
        if (null != getIntent() && TextUtils.isEmpty(userID)) {
            userID = getIntent().getStringExtra(USER_ID_ARG);
        }
        return userID;
    }

    private void setUI() {

        userID = getUserId();
        phoneNumber = getPhoneNumber();

        if (phoneNumber != null) {
            etMobileNumber.setText(phoneNumber);
            etMobileNumber.setSelection(phoneNumber.length());
        }

    }
}
