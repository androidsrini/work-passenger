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

public class UpdatePasswordFragment extends Fragment {

    private static int width, height;
    TextView tvUpdatePassword;
    EditText etPassword, etConfirmPassword;
    Button btnUpdate;
    ImageView toolbarClose;
    RelativeLayout rlMain;

    public static UpdatePasswordFragment newInstance(Activity activity, int screenWidth, int screenHeight) {
        UpdatePasswordFragment fragment = new UpdatePasswordFragment();
        Bundle bundle = new Bundle();
        width = screenWidth;
        height = screenHeight;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_password_profile, container,
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

        RelativeLayout.LayoutParams ll_mobile_numberLayoutParams = (RelativeLayout.LayoutParams) etPassword.getLayoutParams();
        ll_mobile_numberLayoutParams.setMargins(0, topBottomSpace * 5, 0, 0);
        etPassword.setLayoutParams(ll_mobile_numberLayoutParams);

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
    }

    private void setupUI(View view) {
        tvUpdatePassword = view.findViewById(R.id.tvUpdatePassword);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        toolbarClose = view.findViewById(R.id.toolbarClose);
        rlMain = view.findViewById(R.id.rlMain);
    }

}