package com.codesense.passengerapp.ui.emergency;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.ui.socialaccount.SocialAccountAdpater;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

public class EmergencyActivity extends BaseActivity {

    @Initialize(R.id.toolbarClose)
    ImageView toolbarClose;
    @Initialize(R.id.tvTitle)
    TextView tvTitle;
    @Initialize(R.id.tvDescText)
    TextView tvDescText;
    @Initialize(R.id.recyclerView)
    RecyclerView recyclerView;
    @Initialize(R.id.clMain)
    ConstraintLayout clMain;
    @Initialize(R.id.clAfterAddContact)
    ConstraintLayout clAfterAddContact;
    @Initialize(R.id.imgEmergencyProfile)
    ImageView imgEmergencyProfile;
    @Initialize(R.id.tvonlymaxContact)
    TextView tvonlymaxContact;
    @Initialize(R.id.btnAddContact)
    Button btnAddContact;

    BottomSheetDialog bottomSheetDialog;
    View view;
    RelativeLayout rl_title_txt;
    TextView tv_transfer_title,tvMainTxt;
    ImageView imgCross,imgEmergency;
    EditText etName,etNumber;
    Button btnAdd;

    @Override
    protected int layoutRes() {
        return R.layout.activity_emergency;
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

        RelativeLayout.LayoutParams btnSetProfileParams = (RelativeLayout.LayoutParams) btnAddContact.getLayoutParams();
        btnSetProfileParams.setMargins(0, 0, 0, topBottomSpace * 2);
        btnAddContact.setLayoutParams(btnSetProfileParams);
    }

    private void setUI() {

        tvTitle.setText("Emergency Contact");

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new EmergencyAdapter(this, screenWidth, screenHeight));
    }


    @Onclick(R.id.toolbarClose)
    public void toolbarClose(View v) {
        finish();
    }

    @Onclick(R.id.btnAddContact)
    public void btnAddContact(View v) {
        if (clMain.getVisibility() == View.VISIBLE) {
            showBottomSheetDialog();
        }
    }

    @SuppressLint({"ResourceAsColor", "InflateParams", "ResourceType"})
    public void showBottomSheetDialog() {

        view = getLayoutInflater().inflate(R.layout.bottom_sheet_emergency, null);

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);


        int topBottomSpace = (int) (screenWidth * 0.0099);
        int leftBottomSpace = (int) (screenHeight * 0.0109);


        rl_title_txt = view.findViewById(R.id.rl_title_txt);
        imgCross = view.findViewById(R.id.img_cross);
        tvMainTxt = view.findViewById(R.id.tv_main_txt);
        tv_transfer_title = view.findViewById(R.id.tv_transfer_title);
        imgEmergency = view.findViewById(R.id.imgEmergency);
        etName = view.findViewById(R.id.etName);
        etNumber = view.findViewById(R.id.etNumber);
        btnAdd = view.findViewById(R.id.btnAdd);


        btnAdd.setEnabled(false);
        btnAdd.setClickable(false);
        btnAdd.setBackgroundResource(R.color.low_contrast);


        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFieldsForEmptyValues();
            }
        });

        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFieldsForEmptyValues();
            }
        });

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();
                clMain.setVisibility(View.GONE);
                clAfterAddContact.setVisibility(View.VISIBLE);
                tvonlymaxContact.setVisibility(View.VISIBLE);
            }
        });

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();
    }

    @SuppressLint("ResourceType")
    private void checkFieldsForEmptyValues() {
        // TODO Auto-generated method stub
        String s1 = etName.getText().toString();
        String s2 = etNumber.getText().toString();
        if (s1.length() < 1 || s2.length() < 1) {
            btnAdd.setEnabled(false);
            btnAdd.setClickable(false);
            btnAdd.setBackgroundResource(R.color.low_contrast);
        } else {
            btnAdd.setEnabled(true);
            btnAdd.setClickable(true);
            btnAdd.setBackgroundResource(R.color.primary_color);
        }
    }
}
