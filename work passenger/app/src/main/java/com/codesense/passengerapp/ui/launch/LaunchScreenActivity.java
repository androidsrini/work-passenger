package com.codesense.passengerapp.ui.launch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codesense.driverapp.data.CitiesItem;
import com.codesense.driverapp.data.CountriesItem;
import com.codesense.driverapp.data.ILocationAidlInterface;
import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.data.ContinueWithMobileNumberResponse;
import com.codesense.passengerapp.data.CountriesListResponse;
import com.codesense.passengerapp.di.utils.ApiUtility;
import com.codesense.passengerapp.localstoreage.AppSharedPreference;
import com.codesense.passengerapp.localstoreage.DatabaseClient;
import com.codesense.passengerapp.net.ApiResponse;
import com.codesense.passengerapp.net.Constant;
import com.codesense.passengerapp.ui.helper.Utils;
import com.codesense.passengerapp.ui.socialaccount.SocialAccountActivity;
import com.codesense.passengerapp.ui.verifymobile.VerifyMobileActivity;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LaunchScreenActivity extends BaseActivity {

    private static final String TAG = LaunchScreenActivity.class.getName();
    private static final boolean IS_NEED_TO_CALL_SEND_API = false;
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
    @Initialize(R.id.imgNext)
    FloatingActionButton imgNext;
    @Inject
    LaunchScreenViewModel launchScreenViewModel;
    private ILocationAidlInterface iLocationAidlInterface;
    private static  String message;
    @Inject AppSharedPreference appSharedPreference;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
                for (CountriesItem countriesItem : countriesItemList)
                    Log.d(TAG, " The getCountryList: " + countriesItem);
                List<CitiesItem> citiesItems = iLocationAidlInterface.getCitiesList();
                if (citiesItems.isEmpty()) {
                    launchScreenViewModel.fetchCountryList(ApiUtility.getInstance().getAccessTokenMetaData());
                } else {
                    //launchScreenViewModel.fetchCountryList(ApiUtility.getInstance().getAccessTokenMetaData());
                    updateCountryListInDataBase(countriesItemList);
                }
                for (CitiesItem citiesItem: citiesItems)
                    Log.d(TAG, " The cities: " + citiesItem);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            launchScreenViewModel.fetchCountryList(ApiUtility.getInstance().getAccessTokenMetaData());
        }
    }

    /**
     * 1.This method will delete all country data in data base.
     * 2.After delete all data completed it will update new country list data in data base
     * 3.If data base empty then also we are getting onComplete call back.
     *
     * @param countryList we are getting server success and error response.
     */
    private void deleteAllCountriesFromDataBase(List<CountriesItem> countryList) {
        Completable.fromAction(() -> DatabaseClient.getInstance(this).getAppDatabase().countryDao().deleteAllRowFromDataBase())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        // Delete all row is completed.
                        Log.d(TAG, "Country All data is deleted from Data base");
                        /**
                         * Update country list to Data base
                         */
                        updateCountryListInDataBase(countryList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Getting error when delete data
                        Log.d(TAG, "Country All data deleted getting error: " + Log.getStackTraceString(e));
                    }
                });
    }

    /**
     * This method to update country list in data base
     *
     * @param countryList argument
     */
    private void updateCountryListInDataBase(List<CountriesItem> countryList) {
        //compositeDisposable.add();
        Completable.fromAction(() -> DatabaseClient.getInstance(this).getAppDatabase().countryDao().insertAllCountry(countryList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // just like with a Single
                    }

                    @Override
                    public void onComplete() {
                        // action was completed successfully
                        // Country list updated in DB.
                    }

                    @Override
                    public void onError(Throwable e) {
                        // something went wrong
                    }
                });
    }

    private void fetchCountryInDataBase(String countryName) {
        compositeDisposable.add(DatabaseClient.getInstance(this).getAppDatabase().countryDao().findByCountryName(countryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->{
                    Log.d(TAG, "Country name: " + result);
                    launchScreenViewModel.postContinueWithMobileRequest(ApiUtility.getInstance().getAccessTokenMetaData(),
                            Utils.GetInstance().getDeviceId(LaunchScreenActivity.this), result.countryDialCode, getMobileNumber());
                }, error->{
                    Log.d(TAG, "Country name error: " + error);
                }));
    }

    private String getSelectedCountryCode() {
        return countryCodePicker.getSelectedCountryCode();
    }

    public String getMobileNumber() {
        return etMobileNumber.getText().toString().trim();
    }

    private void handleApiResponse(LaunchScreenApiResponse apiResponse) {
        LaunchScreenApiResponse.ServiceType serviceType = apiResponse.getServiceType();
        switch (apiResponse.status) {
            case LOADING:
                Utils.GetInstance().showProgressDialog(this);
                break;
            case SUCCESS:
                Utils.GetInstance().dismissDialog();
                if (LaunchScreenApiResponse.ServiceType.CONTINUE_WITH_MOBILE == serviceType) {
                    ContinueWithMobileNumberResponse continueWithMobileNumberResponse = new Gson().fromJson(apiResponse.data, ContinueWithMobileNumberResponse.class);
                    if (null != continueWithMobileNumberResponse) {
                        //Save user id
                        appSharedPreference.saveUserID(continueWithMobileNumberResponse.getUserId());
                        appSharedPreference.saveAccessToken(continueWithMobileNumberResponse.getAccessToken());
                    }
                    if (ApiResponse.OTP_VALIDATION == apiResponse.getResponseStatus()) {
                        //Show OTP screen
                        VerifyMobileActivity.start(LaunchScreenActivity.this, getMobileNumber(), IS_NEED_TO_CALL_SEND_API);
                    }
                } else if (LaunchScreenApiResponse.ServiceType.COUNTRIES == serviceType) {
                    if (apiResponse.isValidResponse()) {
                        CountriesListResponse countriesListResponse = new Gson().fromJson(apiResponse.data, CountriesListResponse.class);
                        deleteAllCountriesFromDataBase(countriesListResponse.getCountries());
                    }
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
                    //VerifyMobileActivity.start(LaunchScreenActivity.this, getMobileNumber());
                    //etMobileNumber.setText("");

                }

            }
        });
        startService();
        fetchDBInformation();
        Log.d(TAG, "Selected country code: " + countryCodePicker.getSelectedCountryName());
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

    @Onclick(R.id.imgNext)
    public void imgNext(View v) {
        if (isValidatePhoneNumber()) {
            //VerifyMobileActivity.start(LaunchScreenActivity.this, getMobileNumber(), IS_NEED_TO_CALL_SEND_API);
            fetchCountryInDataBase(countryCodePicker.getSelectedCountryName());
        }else{
            Toast.makeText(LaunchScreenActivity.this,message,Toast.LENGTH_SHORT).show();
        }
    }


public boolean isValidatePhoneNumber(){

        if (etMobileNumber.getText().toString().trim().length()==10){
           return true;
        }else{
            message = "Invalid Mobile Number";
            return false;
        }
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
