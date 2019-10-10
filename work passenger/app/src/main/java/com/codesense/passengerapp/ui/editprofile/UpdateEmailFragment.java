package com.codesense.passengerapp.ui.editprofile;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;

public class UpdateEmailFragment extends Fragment {

    private static int width, height;
    TextView tvUpdateEmail;
    EditText etEmail,etEmailVerify;
    Button btnUpdate;
    ImageView toolbarClose;
    RelativeLayout rlMain,rlVerification;

    public static UpdateEmailFragment newInstance(Activity activity, int screenWidth, int screenHeight) {
        UpdateEmailFragment fragment = new UpdateEmailFragment();
        Bundle bundle = new Bundle();
        width = screenWidth;
        height = screenHeight;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_email_profile, container,
                false);
        setupUI(v);
        setDynamicValue();
        functionality();
        return v;
    }

    private void setDynamicValue() {

        int topBottomSpace = (int) (height * 0.0089);
        int imgIconWidth = (int) (width * 0.075);
        int imgIconHeight = (int) (width * 0.075);

        RelativeLayout.LayoutParams imgLayParams = (RelativeLayout.LayoutParams) toolbarClose.getLayoutParams();
        imgLayParams.width = imgIconWidth;
        imgLayParams.height = imgIconHeight;
        toolbarClose.setLayoutParams(imgLayParams);

        RelativeLayout.LayoutParams rlMainLayoutParams = (RelativeLayout.LayoutParams) rlMain.getLayoutParams();
        rlMainLayoutParams.setMargins(topBottomSpace * 3, topBottomSpace * 5, topBottomSpace * 3, 0);
        rlMain.setLayoutParams(rlMainLayoutParams);

        RelativeLayout.LayoutParams ll_mobile_numberLayoutParams = (RelativeLayout.LayoutParams) etEmail.getLayoutParams();
        ll_mobile_numberLayoutParams.setMargins(0, topBottomSpace * 5, 0, 0);
        etEmail.setLayoutParams(ll_mobile_numberLayoutParams);

        RelativeLayout.LayoutParams btnUpdateLayoutParams = (RelativeLayout.LayoutParams) btnUpdate.getLayoutParams();
        btnUpdateLayoutParams.setMargins(0, 0, 0, topBottomSpace * 5);
        btnUpdate.setLayoutParams(btnUpdateLayoutParams);

    }

    private void functionality() {
        toolbarClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        etEmailVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlVerification.setVisibility(View.GONE);
                rlMain.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupUI(View view) {
        tvUpdateEmail = view.findViewById(R.id.tvUpdateEmail);
        etEmailVerify = view.findViewById(R.id.etEmailVerify);
        etEmail = view.findViewById(R.id.etEmail);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        toolbarClose = view.findViewById(R.id.toolbarClose);
        rlMain = view.findViewById(R.id.rlMain);
        rlVerification = view.findViewById(R.id.rlVerification);
    }



}

