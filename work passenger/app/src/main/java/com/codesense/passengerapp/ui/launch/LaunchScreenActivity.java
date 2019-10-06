package com.codesense.passengerapp.ui.launch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.driverapp.data.CitiesItem;
import com.codesense.driverapp.data.CountriesItem;
import com.codesense.driverapp.data.ILocationAidlInterface;
import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.di.utils.ApiUtility;
import com.codesense.passengerapp.net.ApiResponse;
import com.codesense.passengerapp.net.Constant;
import com.codesense.passengerapp.ui.helper.Utils;
import com.codesense.passengerapp.ui.socialaccount.SocialAccountActivity;
import com.codesense.passengerapp.ui.verifymobile.VerifyMobileActivity;
import com.hbb20.CountryCodePicker;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

import java.util.List;

import javax.inject.Inject;

public class LaunchScreenActivity extends BaseActivity {

    private static final String TAG = LaunchScreenActivity.class.getName();
    @Initialize(R.id.country)
    CountryCodePicker countryCodePicker;
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
    @Inject LaunchScreenViewModel launchScreenViewModel;
    private ILocationAidlInterface iLocationAidlInterface;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iLocationAidlInterface = ILocationAidlInterface.Stub.asInterface(service);
            //Toast.makeText(getApplicationContext(),	"Service Connected", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            iLocationAidlInterface = null;
            //Toast.makeText(getApplicationContext(), "Service Disconnected", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * This method to create location service object and start service
     */
    private void startService() {
        Intent it = new Intent();
        it.setAction(Constant.LOCATION_SERVICE);
        it.setPackage(Constant.LOCATION_SERVICE_PACKAGE);
        bindService(it, connection, Context.BIND_AUTO_CREATE);
    }

    /**
     * This method to fetch country and city value from DB.
     */
    private void fetchDBInformation() {
        if (null != iLocationAidlInterface) {
            try {
                Log.d(TAG, " The add number: " + iLocationAidlInterface.addNumbers(10, 20));
                List<CountriesItem> countriesItemList = iLocationAidlInterface.getCountryList();
                for (CountriesItem countriesItem: countriesItemList)
                    Log.d(TAG, " The getCountryList: " + countriesItem);
                List<CitiesItem> citiesItems = iLocationAidlInterface.getCitiesList();
                for (CitiesItem citiesItem: citiesItems)
                    Log.d(TAG, " The cities: " + citiesItem);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private String getSelectedCountryCode() {
        return countryCodePicker.getSelectedCountryCode();
    }

    public String getMobileNumber() {
        return etMobileNumber.getText().toString().trim();
    }

    private void handleApiResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                Utils.GetInstance().showProgressDialog(this);
                break;
            case SUCCESS:
                Utils.GetInstance().dismissDialog();
                if (ApiResponse.OTP_VALIDATION == apiResponse.getResponseStatus()) {
                    //Show OTP screen
                    VerifyMobileActivity.start(LaunchScreenActivity.this, getMobileNumber());
                }
                break;
            case ERROR:
                Utils.GetInstance().dismissDialog();
                break;
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBindView.bind(this);
        launchScreenViewModel.getApiResponseMutableLiveData().observe(this, this::handleApiResponse);
        setDynamicValue();
        functionality();
        //countryCodePicker.setClickable(false);
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
                    // post mobile number to server and validate response.
                    VerifyMobileActivity.start(LaunchScreenActivity.this, getMobileNumber());
                    /*launchScreenViewModel.postContinueWithMobileRequest(ApiUtility.getInstance().getAccessTokenMetaData(),
                            getSelectedCountryCode(), getMobileNumber());*/
                    //etMobileNumber.setText("");
                }

            }
        });
        startService();
        fetchDBInformation();
        launchScreenViewModel.fetchCountryList(ApiUtility.getInstance().getAccessTokenMetaData());
        //Log.d(TAG, "Selected country code: " + countryCodePicker.getSelectedCountryCode());
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
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                //Alert.showMessage(RegistrationActivity.this, countryCodePicker.getSelectedCountryCodeWithPlus());
              String  selected_country_code = countryCodePicker.getSelectedCountryCodeWithPlus();
            }
        });
    }*/
}
