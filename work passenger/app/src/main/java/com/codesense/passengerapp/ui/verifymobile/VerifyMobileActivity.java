package com.codesense.passengerapp.ui.verifymobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
import com.codesense.passengerapp.net.RequestHandler;
import com.codesense.passengerapp.ui.editmobilenumber.EditMobileNumberActivity;
import com.codesense.passengerapp.ui.getname.GetNameActivity;
import com.codesense.passengerapp.ui.helper.Utils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VerifyMobileActivity extends BaseActivity {

    private static final String TAG = "Driver";
    private static final String USER_ID_ARG = "UserID";
    private static final String PHONE_NUMBER_ARG = "PhoneNumber";
    private static final String NEED_TO_CALL_SEND_API_ARG = "NeedToCallSendApi";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Initialize(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @Initialize(R.id.optNumber1)
    EditText optNumber1;
    @Initialize(R.id.optNumber2)
    EditText optNumber2;
    @Initialize(R.id.optNumber3)
    EditText optNumber3;
    @Initialize(R.id.optNumber4)
    EditText optNumber4;
    @Initialize(R.id.btnEditNumber)
    Button btnEditNumber;
    @Initialize(R.id.btnResend)
    Button btnResend;
    @Initialize(R.id.imgNext)
    ImageButton imgNext;
    @Initialize(R.id.tvTitle)
    TextView tvTitle;
    @Initialize(R.id.view)
    View view;
    @Initialize(R.id.view1)
    View view1;
    @Initialize(R.id.view2)
    View view2;
    @Initialize(R.id.view3)
    View view3;
    @Initialize(R.id.toolbarClose)
    ImageView toolbarClose;
    @Initialize(R.id.errorResponseStripTextView)
    TextView errorResponseStripTextView;
    private String userID, phoneNumber;
    @Inject RequestHandler requestHandler;

    private long timeCountInMilliSeconds = 60000;
    private int timeCountProgressSeconds = 1;
    private TimerStatus timerStatus = TimerStatus.STOPPED;
    private CountDownTimer countDownTimer;
    private boolean isValiedAllFields;
    private boolean doesNeedToCallSendApi;

    public static void start(Context context, String phoneNumber, boolean doesNeedToCallSendApi) {
        Intent starter = new Intent(context, VerifyMobileActivity.class);
        starter.putExtra(PHONE_NUMBER_ARG, phoneNumber);
        starter.putExtra(NEED_TO_CALL_SEND_API_ARG, doesNeedToCallSendApi);
        context.startActivity(starter);
    }

    /**
     * This method is used for to send OTP request server.
     */
    private void sendOTPRequest() {
        compositeDisposable.add(requestHandler.sendOtpRequest(ApiUtility.getInstance().getAccessTokenMetaData())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d -> apiResponseHandle(ApiResponse.loading(), ServiceType.SENT_OTP))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            apiResponseHandle(ApiResponse.success(result), ServiceType.SENT_OTP);
                        },
                        error -> {
                            apiResponseHandle(ApiResponse.error(error), ServiceType.SENT_OTP);
                        }));
    }

    /**
     * This method is used for to verify opt value with server
     *
     * @param otp
     */
    private void verifyOTPRequest(String otp) {
        compositeDisposable.add(requestHandler.verifyOtpRequest(ApiUtility.getInstance().getAccessTokenMetaData(), otp)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d -> apiResponseHandle(ApiResponse.loading(), ServiceType.VERIFY_OTP))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> apiResponseHandle(ApiResponse.success(result), ServiceType.VERIFY_OTP),
                        error -> apiResponseHandle(ApiResponse.error(error), ServiceType.VERIFY_OTP)));
    }

    /**
     * This method will handle verify mobile screen api responses.
     * @param apiResponse
     * @param serviceType
     */
    private void apiResponseHandle(ApiResponse apiResponse, ServiceType serviceType) {
        switch (apiResponse.status) {
            case LOADING:
                Utils.GetInstance().showProgressDialog(this);
                break;
            case SUCCESS:
                Utils.GetInstance().dismissDialog();
                if (ServiceType.SENT_OTP == serviceType) {
                    //Start countdown timer
                    if (apiResponse.getResponseStatus() != ApiResponse.OTP_VALIDATION) {
                        startStop();
                    } else {
                        //Disabled resend button because of 'OTP_VALIDATION'
                        btnResend.setEnabled(false);
                    }
                } else if (ServiceType.VERIFY_OTP == serviceType) {
                    if (apiResponse.isValidResponse()) {
                        GetNameActivity.start(this);
                        finish();
                        //AgreementActivity.start(this);
                        //To finish this class.
                        //finish();
                    } else {
                        //updateErrorMessageStripUI(apiResponse.getResponseMessage());
                    }
                }
                break;
            case ERROR:
                Utils.GetInstance().dismissDialog();
                if (ServiceType.SENT_OTP == serviceType) {
                    //Start countdown timer
                    startStop();
                }
                break;
        }
    }

    /**
     * This method to observe verfication code edit text and call verfiy api.
     */
    @UiThread
    private void setEditTextObserver() {
        // Show button Active code when enough fields active code
        Observable<Boolean> mObsPhoneVerify1 = RxTextView.textChanges(optNumber1)
                .observeOn(AndroidSchedulers.mainThread())
                .map(charSequence -> {
                            boolean isField = null != charSequence && !charSequence.toString().equals("");
                            boolean nextViewIsEmpty =  TextUtils.isEmpty(optNumber2.getText());
                            if (isField) {
                                optNumber1.setSelection(charSequence.length());
                                if (nextViewIsEmpty) {
                                    optNumber2.requestFocus();
                                }
                            }
                            return isField;
                        }
                );
        Observable<Boolean> mObsPhoneVerify2 = RxTextView.textChanges(optNumber2)
                .observeOn(AndroidSchedulers.mainThread())
                .map(charSequence -> {
                    boolean isField = null != charSequence && !charSequence.toString().equals("");
                    boolean nextViewIsEmpty =  TextUtils.isEmpty(optNumber3.getText());
                    boolean previousViewIsEmpty =  TextUtils.isEmpty(optNumber1.getText());
                    if (isField) {
                        optNumber2.setSelection(charSequence.length());
                        if (nextViewIsEmpty) {
                            optNumber3.requestFocus();
                        }
                    } else {
                        if (!previousViewIsEmpty) {
                            optNumber1.requestFocus();
                        }
                    }
                    return isField;
                });
        Observable<Boolean> mObsPhoneVerify3 = RxTextView.textChanges(optNumber3)
                .observeOn(AndroidSchedulers.mainThread())
                .map(charSequence -> {
                    boolean isField = null != charSequence && !charSequence.toString().equals("");
                    boolean nextViewIsEmpty =  TextUtils.isEmpty(optNumber4.getText());
                    boolean previousViewIsEmpty =  TextUtils.isEmpty(optNumber2.getText());
                    if (isField) {
                        optNumber3.setSelection(charSequence.length());
                        if (nextViewIsEmpty) {
                            optNumber4.requestFocus();
                        }
                    } else {
                        if (!previousViewIsEmpty) {
                            optNumber2.requestFocus();
                        }
                    }
                    return isField;
                });
        Observable<Boolean> mObsPhoneVerify4 = RxTextView.textChanges(optNumber4)
                .observeOn(AndroidSchedulers.mainThread())
                .map(charSequence -> {
                    //hideKeyboard();
                    boolean isFieldNotEmpty =  charSequence != null && !charSequence.toString().equals("");
                    boolean previousViewIsEmpty =  TextUtils.isEmpty(optNumber3.getText());
                    if (!isFieldNotEmpty)  {
                        if (!previousViewIsEmpty) {
                            optNumber3.requestFocus();
                        }
                    } else {
                        optNumber4.setSelection(charSequence.length());
                    }
                    return isFieldNotEmpty;
                });

        Disposable disposable = Observable
                .combineLatest(mObsPhoneVerify1, mObsPhoneVerify2, mObsPhoneVerify3, mObsPhoneVerify4,
                        (PhoneVerify1, PhoneVerify2, PhoneVerify3, PhoneVerify4)
                                -> PhoneVerify1 && PhoneVerify2 && PhoneVerify3 && PhoneVerify4)
                .subscribe(result -> {
                    isValiedAllFields = result;
                    Log.d(TAG, " The verify observable: " + result);
                });
        compositeDisposable.add(disposable);
    }

    private boolean isDoesNeedToCallSendApi() {
        Intent intent = getIntent();
        if (null != intent) {
            doesNeedToCallSendApi = intent.getBooleanExtra(NEED_TO_CALL_SEND_API_ARG, false);
        }
        return doesNeedToCallSendApi;
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_verify_mobile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBindView.bind(this);
        setDynamicValue();
        functionality();
        updateUI();
        startStop();
        setEditTextObserver();
    }

    /**
     * This method will update Action bar title and append phone number.
     */
    @UiThread
    private void updateUI() {
        tvTitle.setText(getResources().getText(R.string.verify_title));
        tvPhoneNumber.setText(getString(R.string.verify_mobile_text_place_holder, getPhoneNumber()));
    }

    @UiThread
    private void updateErrorMessageStripUI(@NonNull String msg) {
        if (View.INVISIBLE == errorResponseStripTextView.getVisibility())
            errorResponseStripTextView.setVisibility(View.VISIBLE);
        errorResponseStripTextView.setText(msg);
    }

    @UiThread
    private void functionality() {
        optNumber1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                view.setBackgroundResource(R.drawable.view_for_edittext_primary);
                view1.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view2.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view3.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                return false;
            }
        });

        optNumber2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                view.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view1.setBackgroundResource(R.drawable.view_for_edittext_primary);
                view2.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view3.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                return false;
            }
        });

        optNumber1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                view.setBackgroundResource(R.drawable.view_for_edittext_primary);
                view1.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view2.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view3.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
            }
        });

        optNumber2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                view.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view1.setBackgroundResource(R.drawable.view_for_edittext_primary);
                view2.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view3.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
            }
        });

        optNumber3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                view.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view1.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view2.setBackgroundResource(R.drawable.view_for_edittext_primary);
                view3.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
            }
        });
        optNumber4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                view.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view1.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view2.setBackgroundResource(R.drawable.view_for_edittext_lowconstract);
                view3.setBackgroundResource(R.drawable.view_for_edittext_primary);
            }
        });
        if (isDoesNeedToCallSendApi())
            sendOTPRequest();
    }

    @UiThread
    private void setDynamicValue() {
        int topBottomSpace = (int) (screenHeight * 0.0089);
        int imgIconWidth = (int) (screenWidth * 0.075);
        int imgEditheight = (int) (screenWidth * 0.105);
        int imgIconHeight = (int) (screenWidth * 0.075);

        RelativeLayout.LayoutParams imgLayParams = (RelativeLayout.LayoutParams) toolbarClose.getLayoutParams();
        imgLayParams.width = imgIconWidth;
        imgLayParams.height = imgIconHeight;
        toolbarClose.setLayoutParams(imgLayParams);

        ConstraintLayout.LayoutParams tvPhoneNumberLayoutParams = (ConstraintLayout.LayoutParams) tvPhoneNumber.getLayoutParams();
        tvPhoneNumberLayoutParams.setMargins(topBottomSpace * 3, topBottomSpace * 2, topBottomSpace * 2, 0);
        tvPhoneNumber.setLayoutParams(tvPhoneNumberLayoutParams);

        tvPhoneNumber.setPadding(0, 0, topBottomSpace * 3, 0);
        ConstraintLayout.LayoutParams optNumber1LayoutParams = (ConstraintLayout.LayoutParams) optNumber1.getLayoutParams();
        optNumber1LayoutParams.setMargins(topBottomSpace * 3, topBottomSpace * 7, 0, 0);
        optNumber1.setLayoutParams(optNumber1LayoutParams);


        ConstraintLayout.LayoutParams optNumber2LayoutParams = (ConstraintLayout.LayoutParams) optNumber2.getLayoutParams();
        optNumber2LayoutParams.setMargins(topBottomSpace * 2, topBottomSpace * 7, topBottomSpace * 2, 0);
        optNumber2.setLayoutParams(optNumber2LayoutParams);

        ConstraintLayout.LayoutParams optNumber3LayoutParams = (ConstraintLayout.LayoutParams) optNumber3.getLayoutParams();
        optNumber3LayoutParams.setMargins(topBottomSpace * 2, topBottomSpace * 7, topBottomSpace * 2, 0);
        optNumber3.setLayoutParams(optNumber3LayoutParams);

        ConstraintLayout.LayoutParams optNumber4LayoutParams = (ConstraintLayout.LayoutParams) optNumber4.getLayoutParams();
        optNumber4LayoutParams.setMargins(topBottomSpace * 3, topBottomSpace * 7, topBottomSpace * 2, 0);
        optNumber4.setLayoutParams(optNumber4LayoutParams);


        ConstraintLayout.LayoutParams viewLayoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        viewLayoutParams.setMargins(topBottomSpace * 3, topBottomSpace, 0, 0);
        view.setLayoutParams(viewLayoutParams);

        ConstraintLayout.LayoutParams view1LayoutParams = (ConstraintLayout.LayoutParams) view1.getLayoutParams();
        view1LayoutParams.setMargins(topBottomSpace * 2, topBottomSpace, topBottomSpace * 2, 0);
        view1.setLayoutParams(view1LayoutParams);

        ConstraintLayout.LayoutParams view2LayoutParams = (ConstraintLayout.LayoutParams) view2.getLayoutParams();
        view2LayoutParams.setMargins(topBottomSpace * 2, topBottomSpace, topBottomSpace * 2, 0);
        view2.setLayoutParams(view2LayoutParams);

        ConstraintLayout.LayoutParams view3LayoutParams = (ConstraintLayout.LayoutParams) view3.getLayoutParams();
        view3LayoutParams.setMargins(topBottomSpace * 2, topBottomSpace, 0, 0);
        view3.setLayoutParams(view3LayoutParams);

        LinearLayout.LayoutParams btnEditNumberLayoutParams = (LinearLayout.LayoutParams) btnEditNumber.getLayoutParams();
        btnEditNumberLayoutParams.height = imgEditheight;
        btnEditNumberLayoutParams.setMargins(topBottomSpace * 3, topBottomSpace * 4, topBottomSpace, 0);
        btnEditNumber.setLayoutParams(btnEditNumberLayoutParams);

        LinearLayout.LayoutParams btnResendLayoutParams = (LinearLayout.LayoutParams) btnResend.getLayoutParams();
        btnResendLayoutParams.height = imgEditheight;
        btnResendLayoutParams.setMargins(topBottomSpace, topBottomSpace * 4, topBottomSpace, 0);
        btnResend.setLayoutParams(btnResendLayoutParams);

        LinearLayout.LayoutParams imgNextLayoutParams = (LinearLayout.LayoutParams) imgNext.getLayoutParams();
        imgNextLayoutParams.setMargins(0, topBottomSpace * 2, topBottomSpace, 0);
        imgNext.setLayoutParams(imgNextLayoutParams);
    }

    /**
     * This method to observe verfication code edit text and call verfiy api.
     */


    /**
     * This method will return user enter otp value
     * @return string.
     */
    private String getVerifyOtp() {
        return optNumber1.getText().toString() + optNumber2.getText().toString() + optNumber3.getText().toString()
                + optNumber4.getText().toString();
    }

    private String getPhoneNumber() {
        if (null != getIntent() && TextUtils.isEmpty(phoneNumber)) {
            phoneNumber = getIntent().getStringExtra(PHONE_NUMBER_ARG);
        }
        return phoneNumber;
    }

    @Onclick(R.id.toolbarClose)
    public void toolbarClose(View v) {
        finish();
    }

    @Onclick(R.id.imgNext)
    public void imgNext(View v) {
        /*Intent intent = new Intent(this, GetNameActivity.class);
        startActivity(intent);*/
        if (isValiedAllFields) {
            verifyOTPRequest(getVerifyOtp());
        } else {
            Utils.GetInstance().showToastMsg("Enter OTP");
        }
    }

    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnResend.setText(hmsTimeFormatter(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                btnResend.setText(getResources().getString(R.string.resend_text));
                timerStatus = TimerStatus.STOPPED;
            }
        }.start();
        countDownTimer.start();
    }

    private String hmsTimeFormatter(long milliSeconds) {
        return String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
    }

    private void startStop() {
        if (timerStatus == TimerStatus.STOPPED) {
            // call to initialize the progress bar values
            timerStatus = TimerStatus.STARTED;
            // call to start the count down timer
            startCountDownTimer();
        } else {
            stopCountDownTimer();
        }
    }

    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    private boolean isCountDownTimerRunning() {
        return timerStatus == TimerStatus.STARTED;
    }

    @Onclick(R.id.btnResend)
    protected void resendOtpRequest(View v) {

    }

    @Onclick(R.id.btnEditNumber)
    protected void editPhoneNumberRequest(View v) {
        EditMobileNumberActivity.start(this,userID,phoneNumber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private enum ServiceType {
        SENT_OTP, VERIFY_OTP
    }
}
