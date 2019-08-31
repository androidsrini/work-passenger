package com.codesense.passengerapp.ui.business;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.ui.drawer.DrawerActivity;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

public class BusinessActivity extends BaseActivity {

    @Initialize(R.id.imgBusinessProfile)
    ImageView imgBusinessProfile;
    @Initialize(R.id.tvDescText)
    TextView tvDescText;
    @Initialize(R.id.btnSetProfile)
    Button btnSetProfile;
    @Initialize(R.id.clMain)
    ConstraintLayout clMain;
    @Initialize(R.id.toolbarClose)
    ImageView toolbarClose;
    @Initialize(R.id.tvTitle)
    TextView tvTitle;

    //After Business
    @Initialize(R.id.clBusinessAfter)
    ConstraintLayout clBusinessAfter;
    @Initialize(R.id.tvSetProfileText)
    TextView tvSetProfileText;
    @Initialize(R.id.rlEmail)
    RelativeLayout rlEmail;
    @Initialize(R.id.rlReports)
    RelativeLayout rlReports;
    @Initialize(R.id.rlPayments)
    RelativeLayout rlPayments;
    @Initialize(R.id.imgEmail)
    ImageView imgEmail;
    @Initialize(R.id.imgReport)
    ImageView imgReport;
    @Initialize(R.id.tvEmail)
    TextView tvEmail;
    @Initialize(R.id.tvEmailValue)
    TextView tvEmailValue;
    @Initialize(R.id.tvReport)
    TextView tvReport;
    @Initialize(R.id.imgEdit)
    ImageView imgEdit;
    @Initialize(R.id.imgArrow)
    ImageView imgArrow;
    @Initialize(R.id.imgPayment)
    ImageView imgPayment;
    @Initialize(R.id.imgArrowPayment)
    ImageView imgArrowPayment;
    @Initialize(R.id.tvReportValue)
    TextView tvReportValue;
    @Initialize(R.id.tvPayment)
    TextView tvPayment;
    @Initialize(R.id.tvPaymentValue)
    TextView tvPaymentValue;
    @Initialize(R.id.btnSetProfileAfter)
    Button btnSetProfileAfter;

    @Override
    protected int layoutRes() {
        return R.layout.activity_business;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBindView.bind(this);
        setDynamicValue();
        tvTitle.setText(getResources().getString(R.string.business_title_text));
    }

    private void setDynamicValue() {

        int imgIconWidth = (int) (screenWidth * 0.505);
        int imgIconHeight = (int) (screenWidth * 0.505);
        int imgIconSecondWidth = (int) (screenWidth * 0.095);
        int imgIconSecondHeight = (int) (screenWidth * 0.095);
        int imgArrowWidth = (int) (screenWidth * 0.075);
        int imgArrowHeight = (int) (screenWidth * 0.075);
        int imgArrowSecondWidth = (int) (screenWidth * 0.075);
        int imgArrowSecondHeight = (int) (screenWidth * 0.075);

        int topBottomSpace = (int) (screenHeight * 0.0089);


        RelativeLayout.LayoutParams imgLayParams = (RelativeLayout.LayoutParams) toolbarClose.getLayoutParams();
        imgLayParams.width = imgArrowWidth;
        imgLayParams.height = imgArrowHeight;
        toolbarClose.setLayoutParams(imgLayParams);


        RelativeLayout.LayoutParams rlMainLayParams = (RelativeLayout.LayoutParams) clMain.getLayoutParams();
        rlMainLayParams.setMargins(topBottomSpace * 3, 0, topBottomSpace * 3, 0);
        clMain.setLayoutParams(rlMainLayParams);

        ConstraintLayout.LayoutParams btnSetProfileParams = (ConstraintLayout.LayoutParams) btnSetProfile.getLayoutParams();
        btnSetProfileParams.setMargins(0, 0, 0, topBottomSpace * 8);
        btnSetProfile.setLayoutParams(btnSetProfileParams);

        ConstraintLayout.LayoutParams imgArrowLayParams = (ConstraintLayout.LayoutParams) imgBusinessProfile.getLayoutParams();
        imgArrowLayParams.width = imgIconWidth;
        imgArrowLayParams.height = imgIconHeight;
        imgBusinessProfile.setLayoutParams(imgArrowLayParams);

        //After
        rlEmail.setPadding(topBottomSpace * 2, topBottomSpace * 2, topBottomSpace * 2, topBottomSpace * 2);
        rlReports.setPadding(topBottomSpace * 2, topBottomSpace * 2, topBottomSpace * 2, topBottomSpace * 2);
        rlPayments.setPadding(topBottomSpace * 2, topBottomSpace * 2, topBottomSpace * 2, topBottomSpace * 2);

        ConstraintLayout.LayoutParams tvSetProfileTextLayParams = (ConstraintLayout.LayoutParams) tvSetProfileText.getLayoutParams();
        tvSetProfileTextLayParams.setMargins(topBottomSpace * 3, 0, topBottomSpace * 3, 0);
        tvSetProfileText.setLayoutParams(tvSetProfileTextLayParams);

        ConstraintLayout.LayoutParams btnSetProfileAfterParams = (ConstraintLayout.LayoutParams) btnSetProfileAfter.getLayoutParams();
        btnSetProfileAfterParams.setMargins(topBottomSpace * 3, 0, topBottomSpace * 3, topBottomSpace * 8);
        btnSetProfileAfter.setLayoutParams(btnSetProfileAfterParams);

        RelativeLayout.LayoutParams imgEmailLayParams = (RelativeLayout.LayoutParams) imgEmail.getLayoutParams();
        imgEmailLayParams.width = imgIconSecondWidth;
        imgEmailLayParams.height = imgIconSecondHeight;
        imgEmail.setLayoutParams(imgEmailLayParams);

        RelativeLayout.LayoutParams imgReportLayParams = (RelativeLayout.LayoutParams) imgReport.getLayoutParams();
        imgReportLayParams.width = imgIconSecondWidth;
        imgReportLayParams.height = imgIconSecondHeight;
        imgReport.setLayoutParams(imgReportLayParams);

        RelativeLayout.LayoutParams imgPaymentLayParams = (RelativeLayout.LayoutParams) imgPayment.getLayoutParams();
        imgPaymentLayParams.width = imgIconSecondWidth;
        imgPaymentLayParams.height = imgIconSecondHeight;
        imgPayment.setLayoutParams(imgPaymentLayParams);

        RelativeLayout.LayoutParams imgEditLayParams = (RelativeLayout.LayoutParams) imgEdit.getLayoutParams();
        imgEditLayParams.width = imgArrowSecondWidth;
        imgEditLayParams.height = imgArrowSecondHeight;
        imgEdit.setLayoutParams(imgEditLayParams);

        RelativeLayout.LayoutParams imgArrowLayParam = (RelativeLayout.LayoutParams) imgArrow.getLayoutParams();
        imgArrowLayParam.width = imgArrowSecondWidth;
        imgArrowLayParam.height = imgArrowSecondHeight;
        imgArrow.setLayoutParams(imgArrowLayParam);

        RelativeLayout.LayoutParams imgArrowPaymentLayParam = (RelativeLayout.LayoutParams) imgArrowPayment.getLayoutParams();
        imgArrowPaymentLayParam.width = imgArrowSecondWidth;
        imgArrowPaymentLayParam.height = imgArrowSecondHeight;
        imgArrowPayment.setLayoutParams(imgArrowPaymentLayParam);

    }

//    @Onclick(R.id.toolbarClose)
//    private void toolbarClose() {
//        finish();
//    }

}
