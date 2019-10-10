package com.codesense.passengerapp.ui.getname;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.ui.agreement.AgreementActivity;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

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

    @Override
    protected int layoutRes() {
        return R.layout.activity_get_name;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBindView.bind(this);
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
        viewLayout.setMargins(topBottomSpace*3, topBottomSpace * 6, topBottomSpace*3, 0);
        ll_name.setLayoutParams(viewLayout);

        ConstraintLayout.LayoutParams imgNextLayout = (ConstraintLayout.LayoutParams) imgNext.getLayoutParams();
        imgNextLayout.setMargins(0, topBottomSpace * 9, topBottomSpace*3, 0);
        imgNext.setLayoutParams(imgNextLayout);

    }

    @Onclick(R.id.toolbarClose)
    public void toolbarClose(View v) {
        finish();
    }

    @Onclick(R.id.imgNext)
    public void imgNext(View v) {
        Intent intent = new Intent(this, AgreementActivity.class);
        startActivity(intent);
        finish();
    }
}
